package com.be.documentsearchservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PineconeResponseDto {
    private List<DocumentMatchDto> matches;
    private String namespace;
    private String content;
}
