package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.EmbeddingResponse;
import com.be.documentsearchservice.dto.UserQuery;
import com.be.documentsearchservice.service.EmbeddingService;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class EmbeddingController {


    private static final Logger log = LoggerFactory.getLogger(EmbeddingService.class);
    private final EmbeddingService embeddingService;
    private float[] embeddingResponse;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService    = embeddingService;
        
    }

    /*open api로 embedding한 값 받아오기만
    @GetMapping("/question")
    public ResponseEntity<EmbeddingResponse> embed(
            //@RequestParam(value = "message", defaultValue = "Tell me a joke") String message
            @RequestBody UserQuery uq
            ) {
        String userId = uq.userId();
        String query = uq.query();
        float[] vec = embeddingService.embed(query);
        return ok(new EmbeddingResponse(query, vec));
    }
    */



    // open api 임베딩해서 받아온 값 -> pinecone search 결과 확인용
    @PostMapping("/query")
    public ResponseEntity<QueryResponseWithUnsignedIndices> search(
            //@RequestParam("message") String message
            @RequestBody UserQuery uq
    ) {
        String userId = uq.userId();
        String query = uq.query();
        float[] vec = embeddingService.embed(query);
        log.info("embedding vector length = {}", vec.length);
        EmbeddingResponse embedding = new EmbeddingResponse(query, vec);
        QueryResponseWithUnsignedIndices resp = embeddingService.search(embedding);
        log.info("Pinecone resp = {}", resp);
        return ok(resp);
    }



}
