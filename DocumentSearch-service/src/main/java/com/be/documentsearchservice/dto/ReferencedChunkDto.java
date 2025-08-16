package com.be.documentsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferencedChunkDto {
    private String text;
    private double chunkIndex;
    private String source; // optional
}