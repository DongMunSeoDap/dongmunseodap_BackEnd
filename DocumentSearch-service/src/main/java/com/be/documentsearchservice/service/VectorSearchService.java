package com.be.documentsearchservice.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectorSearchService {

    private final VectorStore vectorStore;

    public VectorSearchService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * 주어진 쿼리에 대해 Pinecone에서 유사도 검색을 수행합니다.
     * @param query  사용자 입력 문자열
     * @param topK   상위 N개 결과
     */
    public List<Document> search(String query, int topK) {
        SearchRequest req = SearchRequest.builder()
                .query(query)
                .topK(topK)
                .build();
        return vectorStore.similaritySearch(req);
    }
}
