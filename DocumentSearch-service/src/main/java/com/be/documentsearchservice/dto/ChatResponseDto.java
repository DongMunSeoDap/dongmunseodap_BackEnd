package com.be.documentsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponseDto {
    private long userId;
    private String query;
    private String answer;
    private Object metadata;
}