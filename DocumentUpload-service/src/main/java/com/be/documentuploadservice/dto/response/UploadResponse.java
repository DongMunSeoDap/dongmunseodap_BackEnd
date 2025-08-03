package com.be.documentuploadservice.dto.response;

import com.be.documentuploadservice.entity.UploadStatus;
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

  private String fileId;

  private String fileName; // 원본 파일 이름

  private String s3Key; // s3 객체 키

  private LocalDateTime uploadedAt;

  private String uploadedBy;

  private UploadStatus status; // 서버 내부처리 결과

  private String pdfUrl; // s3 버킷 안에 있는 객체 url

  private String message; // 완료 메세지

  @Override
  public String toString() {
    return "UploadResponse{" +
        "documentId='" + documentId + '\'' +
        ", documentName='" + documentName + '\'' +
        ", s3Key='" + s3Key + '\'' +
        ", uploadedAt=" + uploadedAt +
        ", uploadedBy='" + uploadedBy + '\'' +
        ", status=" + status +
        ", pdfUrl='" + pdfUrl + '\'' +
        ", message='" + message + '\'' +
        '}';
  }
}
