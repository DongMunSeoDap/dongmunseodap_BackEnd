package com.be.documentsearchservice.service;

import com.be.documentsearchservice.config.VectorStoreConfig;
import com.be.documentsearchservice.dto.ChatResponseDto;
import com.be.documentsearchservice.dto.QueryRequest;
import com.be.documentsearchservice.dto.RagChunkDto;
import com.be.documentsearchservice.entity.RagChunkEntity;
import com.be.documentsearchservice.mapper.RagChunkMapper;
import com.be.documentsearchservice.repository.RagChunkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatService {

    private final ChatModel chatModel;
    private final ChatMemory chatMemory;
    private final EmbeddingModel embeddingModel;
    private VectorStoreConfig vectorStoreConfig;
    private final RagChunkRepository ragChunkRepository;


    public ChatService(ChatModel chatModel, ChatMemory chatMemory, EmbeddingModel embeddingModel, VectorStoreConfig vectorStoreConfig, RagChunkRepository ragChunkRepository) {
        this.chatModel = chatModel;
        this.chatMemory = chatMemory;
        this.embeddingModel = embeddingModel;
        this.vectorStoreConfig = vectorStoreConfig;
        this.ragChunkRepository = ragChunkRepository;
    }

    public Mono<RagChunkDto> saveChunk(RagChunkDto dto) {
        RagChunkEntity entity = RagChunkMapper.toEntity(dto);
        return ragChunkRepository.save(entity)
                .map(RagChunkMapper::toDto);
    }

    public Flux<RagChunkDto> findAllChunks() {
        return ragChunkRepository.findAll()
                .map(RagChunkMapper::toDto);
    }

    public Mono<RagChunkDto> findById(String id) {
        return ragChunkRepository.findById(id)
                .map(RagChunkMapper::toDto);
    }

    public ChatResponseDto search_ongoing(QueryRequest queryRequest) {
        String query = queryRequest.getContent();
        long userId = queryRequest.getUserId();
        long conversationId = userId;

        // 1. 벡터 스토어와 임베딩 생성
        VectorStore vectorStore = vectorStoreConfig.pineconeVectorStore(embeddingModel);
        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(5).build());

        List<RagChunkEntity> entities = docs.stream()
                .map(doc -> {
                    Map<String, Object> md = doc.getMetadata();
                    return RagChunkEntity.builder()
                            .vectorDBId(doc.getId())
                            .content((String) md.get("document_content"))
                            .distance(asDouble(md.get("distance")))
                            .score(asDouble(md.get("score")))
                            .chunkIndex(asDouble(md.get("chunk_index")))
                            // savedAt은 @CreatedDate에 의해 자동 세팅됨
                            .build();
                })
                .collect(Collectors.toList());

        // 3. ES에 저장
        ragChunkRepository.saveAll(entities)
                .doOnNext(e -> log.info("Saved: {}", e.getVectorDBId()))
                .doOnError(err -> log.error("ES save error", err))
                .subscribe();

        // 4. 프롬프트 컨텍스트 생성
        String chunkContent = entities.stream()
                .map(RagChunkEntity::getContent)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("\n---\n"));


        // 3. GPT 응답 생성
        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                        //QuestionAnswerAdvisor.builder(vectorStore).build()
                )
                .build();

        ChatResponse chatResponse = chatClient.prompt()
                .system("""
                    Your role is to provide answers based on the user manual. 
                    Provide the response in Korean, using plain text without Markdown. 
                    Structure the response in readable paragraphs. 
                    You must write in a way that is specific and easy for the user to follow. 
                    From the end of this sentence onward, prioritize the following user manual's text when providing information, 
                    and make sure to read it until the end before providing your answer.

                    [근거 문장들]
                    %s
                    """.formatted(chunkContent))
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(query)
                .call()
                .chatResponse();


        ChatResponseDto chatResponseDto = new ChatResponseDto(
                userId,
                query,
                chatResponse.getResult().getOutput().getText(),
                chatResponse.getMetadata()
        );

        return chatResponseDto;
    }

    private Double asDouble(Object v) {
        if (v instanceof Number n) return n.doubleValue();
        if (v instanceof String s) try { return Double.parseDouble(s); } catch (Exception ignored) {}
        return null;
    }


}
