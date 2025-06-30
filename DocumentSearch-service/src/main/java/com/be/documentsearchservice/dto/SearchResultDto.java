package com.be.documentsearchservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
// 컨트롤러에서 반환할 DTO
public class SearchResultDto {
    private String        query;
    private List<SimilarDto> results;

    public SearchResultDto(String query, List<SimilarDto> results) {
        this.query   = query;
        this.results = results;
    }
    // getters
}