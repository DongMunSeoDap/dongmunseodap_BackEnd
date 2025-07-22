package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.service.EmbeddingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class EmbeddingController {


    private static final Logger log = LoggerFactory.getLogger(EmbeddingService.class);
    private final EmbeddingService embeddingService;
    private float[] embeddingResponse;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService    = embeddingService;
        
    }


/*
    @GetMapping("/question")
    public ResponseEntity<EmbeddingResponse> embed(
            //@RequestParam(value = "message", defaultValue = "Tell me a joke") String message
            @RequestBody QueryRequest qr;
            ) {
        String userId = qr.userId();
        String query = qr.query();
        float[] vec = embeddingService.embed(query);
        return ok(new EmbeddingResponse(query, vec));
    }*/



}
