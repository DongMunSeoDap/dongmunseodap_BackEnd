// ChatResponseMetadataDto.java
package com.be.documentsearchservice.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatResponseMetadataDto {
    private String id;
    private String model;
    private Usage usage;

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Usage {
        private Integer promptTokens;
        private Integer completionTokens; // or generationTokens
        private Integer totalTokens;
    }
}
