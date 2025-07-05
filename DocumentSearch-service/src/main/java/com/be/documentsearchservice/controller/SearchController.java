package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.service.EmbeddingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class SearchController {

    private final EmbeddingService embeddingService;

    public SearchController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @PostMapping("/search")
    public ResponseEntity<float[]> search(@RequestBody Map<String, String> body) {
        String query = body.get("query");
        float[] embedding = embeddingService.embed(query);
        return ResponseEntity.ok(embedding);
    }
}
