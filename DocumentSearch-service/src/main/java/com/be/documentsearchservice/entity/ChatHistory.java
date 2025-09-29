package com.be.documentsearchservice.entity;

import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.Instant;

@Document(indexName = "chat-history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory {

    @Id
    private String chatId;

    @Field(type = FieldType.Keyword)
    private String conversationId;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Text, analyzer = "nori") // 한국어 검색 가능하게
    private String userQuery;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String ragAnswer;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant timestamp;

    @Field(type = FieldType.Keyword)
    private String model;

    @Field(type = FieldType.Keyword)
    private String promptVersion;

    @Field(type = FieldType.Integer)
    private Integer promptTokens;

    @Field(type = FieldType.Integer)
    private Integer completionTokens;

    @Field(type = FieldType.Integer)
    private Integer totalTokens;
}