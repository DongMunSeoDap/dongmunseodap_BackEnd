package com.be.documentsearchservice.dto;

// User의 질문을 답는 query
public record UserQuery (
    String userId,
    String query
){}
