package com.be.documentsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReferencedChunkDto {
    private String id;
    private String document_content;
    private Double distance;
    private Double chunkIndex;
    private Double score; // optional
    private Object metadata;

}