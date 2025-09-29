package com.be.documentsearchservice.mapper;

import com.be.documentsearchservice.dto.RagChunkDto;
import com.be.documentsearchservice.entity.RagChunkEntity;

public final class RagChunkMapper {
    private RagChunkMapper() {}

    public static RagChunkDto toDto(RagChunkEntity e) {
        if (e == null) return null;
        return RagChunkDto.builder()
                .vectorDBId(e.getVectorDBId())
                .content(e.getContent())
                .distance(e.getDistance())
                .score(e.getScore())
                .chunkIndex(e.getChunkIndex())
                .embeddingVersion(e.getEmbeddingVersion())
                .savedAt(e.getSavedAt())
                .build();
    }

    public static RagChunkEntity toEntity(RagChunkDto d) {
        if (d == null) return null;
        return RagChunkEntity.builder()
                .vectorDBId(d.getVectorDBId())
                .content(d.getContent())
                .distance(d.getDistance())
                .score(d.getScore())
                .chunkIndex(d.getChunkIndex())
                .embeddingVersion(d.getEmbeddingVersion())
                .savedAt(d.getSavedAt())
                .build();
    }
}
