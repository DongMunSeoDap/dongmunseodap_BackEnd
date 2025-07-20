package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.EmbeddingResponse;
import com.be.documentsearchservice.dto.UserQuery;
import com.be.documentsearchservice.service.EmbeddingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



   /* @PostMapping("/query")
    public ResponseEntity<?> search(@RequestBody UserQuery uq) {
        String userId = uq.userId();
        String query = uq.query();

        //  입력 유효성 검증
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Query must not be null or empty");
        }

        float[] vec = embeddingService.embed(query);
        log.info("embedding vector (first 10 values) = {}", Arrays.toString(Arrays.copyOf(vec, 10)));
        log.info("embedding vector length = {}", vec.length);

        EmbeddingResponse embedding = new EmbeddingResponse(query, vec);
        QueryResponseWithUnsignedIndices resp = embeddingService.search(embedding);


        return ResponseEntity.ok(embeddingService.search(embedding));

    }*/



}
