package com.be.documentuploadservice.entity;


import java.time.LocalDateTime;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "file_meta") // 인데스 이름

public class File {

  @Id
  @Field(type = FieldType.Long)
  private Long fileId; // 문서 고유 ID

  // 파일 업로드 관련 정보
  @Field(type = FieldType.Text)
  private String fileName;

  @Field(type = FieldType.Text)
  private String s3Key; // S3 객체 키

  @Field(type = FieldType.Text)
  private String s3Path; // s3 객체 경로

  @Field(type = FieldType.Text)
  private String uploadedBy; // 업로드 한 사람(추후에 user와 매핑)

  @Field(type = FieldType.Date)
  private LocalDateTime uploadedAt; // 절대 시점

  // 문서 메타 정보
  @Field(type = FieldType.Text)
  private String mimeType; // 확장자

  @Field(type = FieldType.Text)
  private String language; // 언어

  // 벡터DB와 연동을 위한 필드
  @Field(type = FieldType.Text)
  private String nameSpace; // 민혁님이 정한 namespace 기준과 동일

  // 업로드 상태
  @Field(type = FieldType.Keyword)
  private UploadStatus status; // SUCCESS or FAILURE

  @Field(type = FieldType.Text)
  private String message; // 완료 메시지

}
