package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.SimilarDto;
import com.be.documentsearchservice.service.EmbeddingService;
import com.be.documentsearchservice.service.VectorSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 검색 엔드포인트: 쿼리 → 임베딩 → Pinecone 검색 → 결과 반환
 */
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final EmbeddingService embeddingService;
    private final VectorSearchService vectorSearchService;

    public SearchController(
            EmbeddingService embeddingService,
            VectorSearchService vectorSearchService
    ) {
        this.embeddingService = embeddingService;
        this.vectorSearchService = vectorSearchService;
    }

    @PostMapping
    public ResponseEntity<List<SimilarDto>> search(@RequestBody Map<String,String> body) {
        String query = body.get("query");
        List<Double> vector = embeddingService.embed(query);
        List<SimilarDto> results = vectorSearchService.findSimilar(vector, 5);
        return ResponseEntity.ok(results);
    }
}

