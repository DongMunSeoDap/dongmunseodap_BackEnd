package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.service.EmbeddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("/embedding")
    public ResponseEntity<Map<String, Object>> embed(
            @RequestParam(value = "message", defaultValue = "Hello world") String message
    ) {
        float[] vector = embeddingService.embed(message);
        return ResponseEntity.ok(Map.of(
                "message", message,
                "embedding", vector
        ));
    }
}
