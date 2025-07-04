package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.SimilarDto;
import com.be.documentsearchservice.service.EmbeddingService;
import com.be.documentsearchservice.service.PineconeHttpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    private final EmbeddingService embeddingService;
    private final PineconeHttpService pinecone;

    public SearchController(EmbeddingService embeddingService,
                            PineconeHttpService pinecone) {
        this.embeddingService = embeddingService;
        this.pinecone        = pinecone;
    }

    @PostMapping("/ai/search")
    public ResponseEntity<List<SimilarDto>> search(
            @RequestBody Map<String,String> req
    ) {
        String query = req.get("query");
        float[] vec = embeddingService.embed(query);
        List<SimilarDto> results = pinecone.query(vec, 5);
        return ResponseEntity.ok(results);
    }
}

