package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.QueryRequest;
import com.be.documentsearchservice.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/ai")
public class PineconeController {

    private final ChatService chatService;

    public PineconeController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/query")
    public ResponseEntity<?> ask(@RequestBody QueryRequest queryRequest) {
        return ResponseEntity.ok(chatService.search_ongoing(queryRequest));
    }

}
