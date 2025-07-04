package com.be.documentsearchservice.service;

import com.be.documentsearchservice.dto.SimilarDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
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
            @Value("${spring.ai.vectorstore.pinecone.index-name}") String indexName
    ) {
        this.client = pineconeClient;
        this.indexName = indexName;
    }

    /**
     * Pinecone 벡터 검색 수행
     *
     * @param vector float[] 형태의 임베딩 벡터
     * @param topK   반환할 상위 K개
     * @return id, score, metadata.text 를 담은 SimilarDto 리스트
     */
    @SuppressWarnings("unchecked")
    public List<SimilarDto> query(float[] vector, int topK) {
        // 1) float[] → double[] 변환
        double[] doubleVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            doubleVector[i] = vector[i];
        }

        // 2) 요청 본문 생성
        Map<String, Object> body = Map.of(
                "vector",          doubleVector,
                "topK",            topK,
                "includeMetadata", true
        );

        // 3) Pinecone /query 엔드포인트 호출
        Map<String, Object> resp = client.post()
                .uri("/databases/{index}/query", indexName)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        // 4) 응답 파싱
        List<Map<String, Object>> matches = (List<Map<String, Object>>) resp.get("matches");

        return matches.stream()
                .map(m -> new SimilarDto(
                        m.get("id").toString(),
                        ((Number) m.get("score")).doubleValue(),
                        ((Map<?, ?>) m.get("metadata")).get("text").toString()
                ))
                .collect(Collectors.toList());
    }
}
