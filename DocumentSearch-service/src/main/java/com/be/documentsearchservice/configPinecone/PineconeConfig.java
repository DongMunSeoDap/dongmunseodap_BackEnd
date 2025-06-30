package com.be.documentsearchservice.configPinecone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Pinecone REST API 호출을 위한 WebClient 설정
 */
@Configuration
public class PineconeConfig {

    @Bean
    public WebClient pineconeClient(
            @Value("${pinecone.api-key}") String apiKey,
            @Value("${pinecone.environment}") String environment
    ) {
        return WebClient.builder()
                .baseUrl("https://" + environment + ".pinecone.io")
                .defaultHeader("Api-Key", apiKey)
                .build();
    }
}

