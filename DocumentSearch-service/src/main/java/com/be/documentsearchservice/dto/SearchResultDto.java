package com.be.documentsearchservice.dto;

//벡터 DB 검색 결과를 담는 DTO
public record SearchResultDto(
        String id,
        double score,
        String category,
        String chunkText
) {}