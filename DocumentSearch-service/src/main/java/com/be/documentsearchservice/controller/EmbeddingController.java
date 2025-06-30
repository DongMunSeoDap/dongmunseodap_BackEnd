package com.be.documentsearchservice.controller;


import com.be.documentsearchservice.service.EmbeddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 임베딩 전용 엔드포인트
 */
@RestController
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("/ai/embedding")
    public ResponseEntity<Map<String,Object>> embed(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message
    ) {
        List<Double> vector = embeddingService.embed(message);
        return ResponseEntity.ok(Map.of(
                "message", message,
                "embedding", vector
        ));
    }
}