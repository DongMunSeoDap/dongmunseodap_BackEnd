package com.be.documentsearchservice.service;



import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingClient;

    public EmbeddingService(EmbeddingModel embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    public float[] embed(String text) {
        // 내부적으로 OpenAI API에 embed 요청을 보냅니다.
        return embeddingClient.embed(text);
    }
}
