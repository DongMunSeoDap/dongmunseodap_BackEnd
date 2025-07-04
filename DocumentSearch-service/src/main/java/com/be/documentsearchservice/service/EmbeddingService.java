package com.be.documentsearchservice.service;



import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {
    private final EmbeddingModel embeddingClient;
    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingClient = embeddingModel;
    }
    public float[] embed(String text) {
        return embeddingClient.embed(text);
    }
}
