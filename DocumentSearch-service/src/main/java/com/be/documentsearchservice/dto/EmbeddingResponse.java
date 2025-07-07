package com.be.documentsearchservice.dto;

// open api 임베딩한 값 담는 DTO

public class EmbeddingResponse {
    private final String message;
    public final float[] embedding;

    public EmbeddingResponse(String message, float[] embedding) {
        this.message   = message;
        this.embedding = embedding;

    }

}

