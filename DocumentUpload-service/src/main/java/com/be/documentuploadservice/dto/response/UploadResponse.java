package com.be.documentuploadservice.dto.response;

import com.be.documentuploadservice.entity.UploadStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UploadResponse {

  private String traceId; // 카프카 메세지 추적용 id

  private String s3Key; // s3 객체 키

}