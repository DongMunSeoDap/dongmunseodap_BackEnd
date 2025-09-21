package com.be.documentsearchservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferencedChunkDto {
    private String id;
    private String document_content;
    private Double distance;
    private Double score;
    private Double chunkIndex;
    private String savedAt;// optional

}