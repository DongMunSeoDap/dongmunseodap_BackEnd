package com.be.documentuploadservice.entity;

import com.be.documentuploadservice.dto.response.UploadResponse.UploadStatus;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document { // -> es 연동 시 다시 설정

  private String documentId;

  // 파일 업로드 관련 정보
  private String fileName;
  private String s3Path; // S3 저장 경로

  // 이벤트 컨텍스트 정보
  private String version;
  private String eventType;
  private String traceId;
  private String uploadedBy;
  private Instant uploadedAt; // 절대 시점

  // 문서 메타 정보
  private String mimeType;
  private String language;

  // 업로드 상태
  private UploadStatus status; // SUCCESS or FAILURE
  private String message; // 실패시 메시지

}
