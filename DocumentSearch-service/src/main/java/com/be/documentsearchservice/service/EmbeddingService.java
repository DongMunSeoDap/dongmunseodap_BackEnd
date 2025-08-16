package com.be.documentsearchservice.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingClient;
    private final EmbeddingModel embeddingModel;
    private EmbeddingService embeddingService;
    private static final Logger log = LoggerFactory.getLogger(EmbeddingService.class);
    public EmbeddingService(EmbeddingModel embeddingClient, EmbeddingModel embeddingModel) {
        this.embeddingClient = embeddingClient;
        this.embeddingModel = embeddingModel;
    }

    // open api 연동
    public float[] embed(String text) {
        return embeddingClient.embed(text);
    }


}
