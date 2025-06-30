package com.be.documentsearchservice.service;

import com.be.documentsearchservice.dto.SimilarDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PineconeHttpService {

    private final WebClient client;
    private final String indexName;

    public PineconeHttpService(
            WebClient pineconeClient,
            @Value("${pinecone.index}") String indexName
    ) {
        this.client = pineconeClient;
        this.indexName = indexName;
    }

    /**
     * 벡터 검색 수행
     * @param vector 임베딩 벡터
     * @param topK 반환할 상위 개수
     */
    @SuppressWarnings("unchecked")
    public List<SimilarDto> query(List<Double> vector, int topK) {
        Map<String,Object> body = Map.of(
                "vector", vector,
                "topK", topK,
                "includeMetadata", true
        );

        Map<String,Object> resp = client.post()
                .uri("/databases/{index}/query", indexName)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<Map<String,Object>>() {})
                .block();

        List<Map<String,Object>> matches = (List<Map<String,Object>>) resp.get("matches");

        return matches.stream()
                .map(m -> new SimilarDto(
                        m.get("id").toString(),
                        ((Number) m.get("score")).doubleValue(),
                        ((Map<?,?>) m.get("metadata")).get("text").toString()
                ))
                .collect(Collectors.toList());
    }
}
