package com.be.documentsearchservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PineconeConfig {

    @Bean
    public WebClient pineconeClient(
            @Value("${spring.ai.vectorstore.pinecone.api-key}") String apiKey,
            @Value("${spring.ai.vectorstore.pinecone.environment}") String environment
    ) {
        // Pinecone HTTP endpoint: {environment}.pinecone.io
        String baseUrl = "https://" + environment + ".pinecone.io";
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Api-Key " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }
}
