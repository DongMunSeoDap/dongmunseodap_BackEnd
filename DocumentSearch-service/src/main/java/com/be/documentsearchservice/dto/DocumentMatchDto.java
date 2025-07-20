package com.be.documentsearchservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class DocumentMatchDto {
    private long score;
    private String id;
    private String documentContent;
}
