package com.be.documentsearchservice.controller;

import com.be.documentsearchservice.dto.QueryRequest;
import com.be.documentsearchservice.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Response Estimate", description = "Response Estimate API")
public class PineconeController {

    private final ChatService chatService;

    public PineconeController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/query")
    @Operation(summary = "사용자 정보 및 질문", description = "사용자 측에서 질문을 보내고, 응답을 받을 수 있는 api")
    public ResponseEntity<?> ask(@RequestBody QueryRequest queryRequest) {
        return ResponseEntity.ok(chatService.search_ongoing(queryRequest));
    }

}
