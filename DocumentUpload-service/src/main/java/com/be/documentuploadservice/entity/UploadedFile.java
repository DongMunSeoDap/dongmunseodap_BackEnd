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
import org.springframework.data.elasticsearch.annotations.Field;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "file_meta") // 인데스 이름

public class UploadedFile { // es 저장

  @Id
  @Field(type = FieldType.Keyword)
  private String fileId; // 문서 고유 ID

  // 파일 업로드 관련 정보
  @Field(type = FieldType.Text)
  private String fileName;

  @Field(type = FieldType.Text)
  private String s3Key; // S3 객체 키

  @Field(type = FieldType.Text)
  private String uploadedBy; // 업로드 한 사람(추후에 user와 매핑)

  @Field(type = FieldType.Date)
  private LocalDateTime uploadedAt; // 절대 시점

  // 업로드 상태
  @Field(type = FieldType.Keyword)
  private UploadStatus status; // SUCCESS or FAILURE

  @Field(type = FieldType.Text)
  private String message; // 완료 메시지

}
