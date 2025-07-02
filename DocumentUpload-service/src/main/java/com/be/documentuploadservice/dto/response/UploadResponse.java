package com.be.documentuploadservice.dto.response;

import com.be.documentuploadservice.entity.UplaodStatus;
import java.time.LocalDateTime;
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
public class UploadResponse {

  private String documentId;

  private String documentName;

  private String s3Path; // s3 저장 경로

  private LocalDateTime uploadedAt;

  private String uploadedBy;

  private UplaodStatus status; // 서버 내부처리 결과

  private String message; // FAILURE시 오류 메세지 출력

  private String pdfUrl; // s3 버킷 안에 있는 객체 url

  @Override
  public String toString() {
    return "UploadResponse{documentId='" + documentId + "', status='" + status + "', message='" + message + "'}";
  }
}
