package com.be.documentsearchservice.service;

import com.be.documentsearchservice.dto.SimilarDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PineconeHttpService 를 이용해 검색 결과 매핑
 */
@Service
public class VectorSearchService {

    private final PineconeHttpService pineconeHttpService;

    public VectorSearchService(PineconeHttpService pineconeHttpService) {
        this.pineconeHttpService = pineconeHttpService;
    }

    public List<SimilarDto> findSimilar(List<Double> embedding, int topK) {
        return pineconeHttpService.query(embedding, topK);
    }
}