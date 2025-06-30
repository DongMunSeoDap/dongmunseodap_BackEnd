package com.be.documentuploadservice.entity;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.time.Instant;
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
@org.springframework.data.elasticsearch.annotations.Document(indexName = "documents")
public class Document { // -> es 연동 시 다시 설정

  @Id
  private String documentId; // 문서 고유 ID

  // 파일 업로드 관련 정보
  private String documentName;
  private String s3Path; // S3 저장 경로

  // 이벤트 컨텍스트 정보
  private String version;
  private String eventType;
  private String traceId;
  private String uploadedBy;

  @Field(type = FieldType.Date)
  private Instant uploadedAt; // 절대 시점

  // 문서 메타 정보
  private String mimeType;
  private String language;

  // 업로드 상태
  private UplaodStatus status; // SUCCESS or FAILURE
  private String message; // 실패시 메시지

}
