package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.EmbeddingResponse;
import com.be.documentsearchservice.service.EmbeddingService;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/ai")
public class EmbeddingController {


    private static final Logger log = LoggerFactory.getLogger(EmbeddingService.class);
    private final EmbeddingService embeddingService;
    private float[] embeddingResponse;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService    = embeddingService;
        
    }

    //open api로 embedding한 값 받아오기
    @GetMapping("/embedding")
    public ResponseEntity<EmbeddingResponse> embed(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message
    ) {
        float[] vec = embeddingService.embed(message);
        return ok(new EmbeddingResponse(message, vec));
    }


    // open api 임베딩해서 받아온 값 -> pinecone search 결과 확인용
    @GetMapping("/search")
    public ResponseEntity<QueryResponseWithUnsignedIndices> search(
            @RequestParam("message") String message
    ) {
        float[] vec = embeddingService.embed(message);
        log.info("embedding vector length = {}", vec.length);
        EmbeddingResponse embedding = new EmbeddingResponse(message, vec);
        QueryResponseWithUnsignedIndices resp = embeddingService.search(embedding);
        log.info("Pinecone resp = {}", resp);
        return ok(resp);
    }



}
