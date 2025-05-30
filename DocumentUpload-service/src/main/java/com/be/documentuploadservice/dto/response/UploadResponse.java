package com.be.documentuploadservice.dto.response;

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

  // kafka message에 포합되지 않는 응답 전용 필드
  private UploadStatus status; // 서버 내부처리 결과 (kafka에 존재 여부)

  // kafka message에 포합되지 않는 응답 전용 필드
  private String message;

  public enum UploadStatus {
    SUCCESS, FAILURE
  }

  @Override
  public String toString() {
    return "UploadResponse{documentId='" + documentId + "', status='" + status + "', message='" + message + "'}";
  }
}
