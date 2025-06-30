package com.be.documentsearchservice.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

/**
 * Spring AI의 EmbeddingModel을 이용해 텍스트를 벡터로 변환하는 서비스
 */
@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    /**
     * 주어진 텍스트를 임베딩 벡터로 변환
     * @param text 입력 텍스트
     * @return 임베딩 벡터 (List<Double>)
     */
    public List<Double> embed(String text) {
        // Spring AI EmbeddingModel 호출
        EmbeddingResponse resp = embeddingModel.embedForResponse(List.of(text));
        // EmbeddingResponse 결과에서 float[] 벡터 추출 (첫 번째 요소)
        float[] raw = resp.getResults().get(0).getOutput();
        // float[]를 List<Double>로 변환
        List<Double> vector = new ArrayList<>(raw.length);
        for (float v : raw) {
            vector.add((double) v);
        }
        return vector;
    }
}
