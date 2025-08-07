package com.be.documentsearchservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatSearchResult {
    private String query;
    private String answer;
    private List<ReferencedChunk> chunks;

    public ChatSearchResult(String query, String answer, List<ReferencedChunk> chunks) {
        this.query = query;
        this.answer = answer;
        this.chunks = chunks;
    }

    // Getters & Setters
}