package com.be.documentsearchservice.dto;


public record SimilarDto(
        String id,
        double score,
        String text
) {}