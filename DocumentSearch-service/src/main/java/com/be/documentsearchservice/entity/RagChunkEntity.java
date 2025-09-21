package com.be.documentsearchservice.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(indexName = "rag-chunks")
public class RagChunkEntity {

    @Id
    private String id;                       // Pinecone id (가능하면 그대로)

    @Field(type = FieldType.Text, analyzer = "standard")
    private String content;                  // chunk 텍스트

    @Field(type = FieldType.Double)
    private Double distance;                 // metadata.distance

    @Field(type = FieldType.Double)
    private Double score;                    // 별도로 오면 score, 없으면 null

    @Field(type = FieldType.Double)
    private Double chunkIndex;              // metadata.chunk_index

    // ✅ 생성 시간 자동 세팅
    @CreatedDate
    @Field(type = FieldType.Date)
    private Instant savedAt;

}