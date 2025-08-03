package com.be.documentuploadservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
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
public class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long documentId; // 문서 고유 ID

  // 파일 업로드 관련 정보
  private String documentName;
  private String s3Key; // S3 객체 키
  private String s3Path; // s3 객체 경로
  private String uploadedBy; // 업로드 한 사람(추후에 user와 매핑)

  @Field(type = FieldType.Date)
  private LocalDateTime uploadedAt; // 절대 시점

  // 문서 메타 정보
  private String mimeType; // 확장자
  private String language; // 언어

  // 벡터DB와 연동을 위한 필드
  private String nameSpace; // 민혁님이 정한 namespace 기준과 동일

  // 업로드 상태
  private UplaodStatus status; // SUCCESS or FAILURE
  private String message; // 완료 메시지

}
