package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.QueryRequest;
import com.be.documentsearchservice.service.ChatService;
import com.be.documentsearchservice.service.PineconeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ai")
public class PineconeController {

    private final PineconeService pineconeService;
    private final ChatService chatService;

    public PineconeController(PineconeService pineconeService, ChatService chatService) {
        this.pineconeService = pineconeService;
        this.chatService = chatService;
    }

    @PostMapping("/query")
    public ChatResponse handleQueryAndSearch(@RequestBody QueryRequest request) {
        // 1. 로그 출력
        System.out.println("Received -> userId: " + request.getUserId() + ", content: " + request.getContent());


        // 2. 검색 실행
        ChatResponse resp = chatService.search_ongoing(request);

        System.out.println("최종 결과:" + resp);

        return resp;


    }
}
