package com.be.documentsearchservice.service;


import com.be.documentsearchservice.config.VectorStoreConfig;
import com.be.documentsearchservice.dto.QueryRequest;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PineconeService {

    private final VectorStoreConfig vectorStoreConfig;
    private final EmbeddingModel embeddingModel;

    public PineconeService(VectorStoreConfig vectorStoreConfig,EmbeddingModel embeddingModel) {
        this.vectorStoreConfig = vectorStoreConfig;
        this.embeddingModel = embeddingModel;
    }

    public String inQuery(QueryRequest qr){
        String content = qr.getContent();
        return content;
    }

    public List<Document> pineconeSearch(QueryRequest queryRequest) {
        String content = queryRequest.getContent();

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Query content must not be null or blank");
        }

        return vectorStoreConfig.pineconeVectorStore(embeddingModel).similaritySearch(
                SearchRequest.builder().query(content).topK(3).build()
        );
    }
}
