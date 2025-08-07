package com.be.documentsearchservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReferencedChunk {
    private String content;
    private Map<String, Object> metadata;
}