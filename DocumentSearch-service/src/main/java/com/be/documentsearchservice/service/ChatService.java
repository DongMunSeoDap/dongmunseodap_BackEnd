package com.be.documentsearchservice.service;

import com.be.documentsearchservice.config.VectorStoreConfig;
import com.be.documentsearchservice.dto.ChatResponseDto;
import com.be.documentsearchservice.dto.QueryRequest;
import com.be.documentsearchservice.dto.ReferencedChunkDto;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatService {

    private final ChatModel chatModel;
    private final ChatMemory chatMemory;
    private final EmbeddingModel embeddingModel;
    private VectorStoreConfig vectorStoreConfig;

    public ChatService(ChatModel chatModel, ChatMemory chatMemory, EmbeddingModel embeddingModel, VectorStoreConfig vectorStoreConfig) {
        this.chatModel = chatModel;
        this.chatMemory = chatMemory;
        this.embeddingModel = embeddingModel;
        this.vectorStoreConfig = vectorStoreConfig;
    }

    public ChatResponseDto search_ongoing(QueryRequest queryRequest) {
        String query = queryRequest.getContent();
        long userId = queryRequest.getUserId();
        long conversationId = userId;

        // 1. 벡터 스토어와 임베딩 생성
        VectorStore vectorStore = vectorStoreConfig.pineconeVectorStore(embeddingModel);
        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(5).build());

        List<ReferencedChunkDto> chunks = docs.stream()
                .map(doc -> {
                    Map<String, Object> meta = doc.getMetadata();

                    double distance = toDouble(firstNonNull(meta.get("distance"), meta.get("DISTANCE")), 0.0);

                    return new ReferencedChunkDto(
                            doc.getId(),
                            doc.getText(),
                            distance,
                            (Double)doc.getMetadata().get("chunk_index"),
                            doc.getScore(),
                            doc.getMetadata()
                    );
                })
                .collect(Collectors.toList());

        String chunkID = chunks.stream()
                .map(chunk -> "Id: " + chunk.getId())
                .collect(Collectors.joining("\n"));

        String chunkContent = chunks.stream()
                .map(chunk -> "document_content: " + chunk.getDocument_content())
                .collect(Collectors.joining("\n"));

        String chunkDistance = chunks.stream()
                .map(chunk -> "Distance: " + chunk.getDistance())
                .collect(Collectors.joining("\n"));


        String chunkIndex = chunks.stream()
                .map(chunk -> "Index: " + chunk.getChunkIndex())
                .collect(Collectors.joining("\n"));

        String chunkScore = chunks.stream()
                .map(chunk -> "Score: " + chunk.getScore())
                .collect(Collectors.joining("\n"));

        String chunkMetadata = chunks.stream()
                .map(chunk -> "Metadata: " + chunk.getMetadata())
                .collect(Collectors.joining("\n"))
                .toString();

        //log.info(docs.toString());
        log.info(chunkID);
        log.info(chunkContent);
        log.info(chunkDistance);
        log.info(chunkIndex);
        log.info(chunkScore);
        log.info(chunkMetadata);

        // 3. GPT 응답 생성
        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                        //QuestionAnswerAdvisor.builder(vectorStore).build()
                )
                .build();

        ChatResponse chatResponse = chatClient.prompt()
                .system("Your role is to provide answers based on the user manual. Provide the response in Korean, using plain text without Markdown. Structure the response in readable paragraphs. You must write in a way that is specific and easy for the user to follow. From the end of this sentence onward, prioritize the following user manual's text when providing information, and make sure to read it until the end before providing your answer.\n" + chunkContent)
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

    private static Object firstNonNull(Object a, Object b) {
        return (a != null) ? a : b;
    }

    private static double toDouble(Object v, double def) {
        if (v == null) return def;
        if (v instanceof Number n) return n.doubleValue(); // Float/Integer/Long/Double 모두 OK
        if (v instanceof String s) {
            try { return Double.parseDouble(s); } catch (NumberFormatException ignored) {}
        }
        return def;
    }



}
