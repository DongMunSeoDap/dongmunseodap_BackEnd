package com.be.documentsearchservice.dto;

import lombok.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RagChunkDto {
    private String vectorDBId;       // Pinecone id
    private String content;          // chunk 텍스트
    private Double distance;         // metadata.distance
    private Double score;            // 별도로 오면 score, 없으면 null
    private Double chunkIndex;       // metadata.chunk_index
    private String embeddingVersion; // 임베딩 버전 태그
    private Instant savedAt;         // 생성 시각
}
