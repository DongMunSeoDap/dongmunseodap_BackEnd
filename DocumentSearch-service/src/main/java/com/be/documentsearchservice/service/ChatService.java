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
        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder().query(query).build());

        List<ReferencedChunkDto> chunks = docs.stream()
                .map(doc -> new ReferencedChunkDto(
                        doc.getText(),
                        (double) doc.getMetadata().get("chunk_index"),  // 중요!
                        (String) doc.getMetadata().getOrDefault("source", "unknown")
                ))
                .collect(Collectors.toList());

        String chunkContext = chunks.stream()
                .map(chunk -> "- " + chunk.getText())
                .collect(Collectors.joining("\n"));

        // 3. GPT 응답 생성
        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                        //QuestionAnswerAdvisor.builder(vectorStore).build()
                )
                .build();

        ChatResponse chatResponse = chatClient.prompt()
                .system("Please provide the response in Korean, using plain text without Markdown. Structure the response in readable paragraphs. Use only the provided reference documents below as the source of information when answering.\n" + chunkContext)
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


}
