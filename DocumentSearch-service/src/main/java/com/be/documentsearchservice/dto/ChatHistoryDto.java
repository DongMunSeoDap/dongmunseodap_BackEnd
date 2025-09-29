package com.be.documentsearchservice.dto;

import lombok.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatHistoryDto {
    private String chatId;           // GPT 응답의 id
    private String conversationId;   // ← 누락 보완
    private String userId;

    private String userQuery;        // 사용자 질문
    private String ragAnswer;        // ← 엔티티와 케이스 통일

    private Instant timestamp;       // ← 엔티티와 타입 통일(Instant)

    private String model;            // gpt-4o, gpt-4.1-mini 등
    private String promptVersion;    // 프롬프트 버전 스냅샷
    private Integer promptTokens;    // ← nullable 고려 시 Integer
    private Integer completionTokens;
    private Integer totalTokens;
}
