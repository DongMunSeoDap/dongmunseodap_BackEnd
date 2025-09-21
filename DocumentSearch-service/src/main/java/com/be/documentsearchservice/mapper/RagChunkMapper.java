package com.be.documentsearchservice.mapper;

import com.be.documentsearchservice.dto.ReferencedChunkDto;
import com.be.documentsearchservice.entity.RagChunkEntity;

import java.time.format.DateTimeFormatter;

public final class RagChunkMapper {
    private RagChunkMapper() {}

    public static RagChunkEntity toEntity(ReferencedChunkDto dto) {
        if (dto == null) return null;
        return RagChunkEntity.builder()
                .id(dto.getId())
                .content(dto.getDocument_content())
                .distance(dto.getDistance())
                .score(dto.getScore())
                .chunkIndex(dto.getChunkIndex())
                .build();
    }

    public static ReferencedChunkDto toDto(RagChunkEntity e) {
        if (e == null) return null;
        return ReferencedChunkDto.builder()
                .id(e.getId())
                .document_content(e.getContent())
                .distance(e.getDistance())
                .score(e.getScore())
                .chunkIndex(e.getChunkIndex())
                .savedAt(
                        e.getSavedAt() != null
                                ? DateTimeFormatter.ISO_INSTANT.format(e.getSavedAt()) // ISO-8601
                                : null
                )
                .build();
    }
}
