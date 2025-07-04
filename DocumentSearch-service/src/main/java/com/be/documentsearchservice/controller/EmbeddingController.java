package com.be.documentsearchservice.controller;


import com.be.documentsearchservice.service.EmbeddingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path = "/ai/embedding", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> embed(
            @RequestParam(value = "message",
                    required = false,               // ← 명시적으로 optional
                    defaultValue = "Tell me a joke") String message
    ) {
        float[] vector = embeddingService.embed(message);
        return ResponseEntity.ok(Map.of(
                "message",   message,
                "embedding", vector
        ));
    }
}
