package com.be.documentsearchservice.dto;


public record QueryRequest(String query, int topK) {
}
