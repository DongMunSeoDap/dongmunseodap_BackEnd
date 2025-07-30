package com.be.documentsearchservice.service;

import com.be.documentsearchservice.config.VectorStoreConfig;
import com.be.documentsearchservice.dto.QueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import java.util.List;

@Slf4j
@Service
public class ChatService {

    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;
    private VectorStoreConfig vectorStoreConfig;

    public ChatService(ChatModel chatModel, EmbeddingModel embeddingModel, VectorStoreConfig vectorStoreConfig) {
        this.chatModel = chatModel;
        this.embeddingModel = embeddingModel;
        this.vectorStoreConfig = vectorStoreConfig;
    }

    public ChatResponse search(QueryRequest queryRequest) {
        String query = queryRequest.getContent();
        log.info("query: " + query);

        VectorStore vectorStore = vectorStoreConfig.pineconeVectorStore(embeddingModel);

        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(3)
                .build());

        for (Document doc : docs) {
            log.info("Found doc: {}", doc.getMetadata()); // or doc.getMetadata()
        }



        ChatResponse resp = ChatClient.builder(chatModel)
                .build()
                .prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStoreConfig.pineconeVectorStore(embeddingModel)))
                .user(query)
                .call()
                .chatResponse();
        return resp;
    }

}
