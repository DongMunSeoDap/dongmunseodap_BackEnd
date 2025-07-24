package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.QueryRequest;
import com.be.documentsearchservice.service.PineconeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.document.Document;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ai/search")
public class PineconeController {

    private final PineconeService pineconeService;

    public PineconeController(PineconeService pineconeService) {
        this.pineconeService = pineconeService;
    }

    @PostMapping("/query")
    public List<Document> handleQueryAndSearch(@RequestBody QueryRequest request) {
        // 1. 로그 출력
        System.out.println("Received -> userId: " + request.getUserId() + ", content: " + request.getContent());

        // 2. 검색 실행
        List<Document> results = pineconeService.pineconeSearch(request);
        System.out.println("Search result size: " + results.size());
        System.out.println(results);

        // 3. 결과 반환
        return results;
    }


    @PostMapping("/test")
    public ResponseEntity<String> receiveQueries(@RequestBody List<QueryRequest> requests) {
        // 여기선 아무것도 안 하고, 그냥 받기만 함
        // 필요하면 여기서 log 찍는 정도만 가능
        requests.forEach(req ->
                System.out.println("Received -> userId: " + req.getUserId() + ", content: " + req.getContent()));

        return ResponseEntity.ok("Received " + requests.size() + " queries.");
    }

}
