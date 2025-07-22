package com.be.documentsearchservice.service;


import com.be.documentsearchservice.dto.QueryRequest;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PineconeService {

    private final VectorStore vectorStore;

    public PineconeService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> pineconeSearch(QueryRequest qr) {
        List<Document> results = vectorStore.similaritySearch(
                SearchRequest.builder().query(qr.getContent()).topK(5).build()
        );
        System.out.println("Search result size: " + results.size()); // 추가
        return results;
    }
}
