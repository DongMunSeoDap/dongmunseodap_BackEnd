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

    public List<Document> pineconeSearch(QueryRequest qr) {
        List<Document> results = vectorStoreConfig.pineconeVectorStore(embeddingModel).similaritySearch(
                SearchRequest.builder().query(qr.getContent()).topK(3).build()
        );
        return results;
    }
}
