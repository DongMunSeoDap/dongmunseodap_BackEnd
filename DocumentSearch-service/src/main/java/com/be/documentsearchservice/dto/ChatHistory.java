package com.be.documentsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatHistory {
    private String chatId;            // GPT 응답의 id
    private String role;              // USER or ASSISTANT
    private String message;           // content
    private LocalDateTime timestamp;  // 생성 시점
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
}
