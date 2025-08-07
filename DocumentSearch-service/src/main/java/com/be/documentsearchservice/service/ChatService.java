package com.be.documentsearchservice.service;

import com.be.documentsearchservice.config.VectorStoreConfig;
import com.be.documentsearchservice.dto.QueryRequest;
import com.be.documentsearchservice.dto.ReferencedChunk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
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
/*    public ChatResponse search(QueryRequest queryRequest) {
        String query = queryRequest.getContent();
        String userId = queryRequest.getUserId();
        log.info("query: " + query);

        VectorStore vectorStore = vectorStoreConfig.pineconeVectorStore(embeddingModel);

        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(3)
                .build());

        for (Document doc : docs) {
            log.info("📎 metadata: {}", doc.getMetadata().get("chunk_index"));
        }


        ChatResponse resp = ChatClient.builder(chatModel)
                .build()
                .prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStoreConfig.pineconeVectorStore(embeddingModel)))
                .user(query)
                .call()
                .chatResponse();
        return resp;
    }*/

    /*public ChatResponse search_ongoing(QueryRequest queryRequest) {
        String query = queryRequest.getContent();
        String userId = queryRequest.getUserId();
        var conversationId = userId;

        var chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStoreConfig.pineconeVectorStore(embeddingModel)).build()
                )
                .build();



        ChatResponse response = chatClient.prompt()
                // Set advisor parameters at runtime
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(query)
                .call()
                .chatResponse();

        return response;
    }*/

    public String search_ongoing(QueryRequest queryRequest) {
        String query = queryRequest.getContent();
        String userId = queryRequest.getUserId();
        String conversationId = userId;

        // 1. 벡터 스토어와 임베딩 생성
        VectorStore vectorStore = vectorStoreConfig.pineconeVectorStore(embeddingModel);
        float[] embedding = embeddingModel.embed(query);

        // 2. 청크 검색
        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(3)
                .build());

        // 3. GPT 응답 생성
        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore).build()
                )
                .build();

        ChatResponse chatResponse = chatClient.prompt()
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(query)
                .call()
                .chatResponse();

        // ✅ 4. chunk 리스트 추출
        List<ReferencedChunk> chunks = docs.stream()
                .map(doc -> new ReferencedChunk(doc.getText(), doc.getMetadata()))
                .collect(Collectors.toList());

        log.info("chunks size: {}", chunks.size());
        for (ReferencedChunk chunk : chunks) {
            log.info("chunk: {}", chunk.getMetadata());
        }

        // 5. 응답 구조로 반환
        return chatResponse.getResult().getOutput().getText();
    }


}
