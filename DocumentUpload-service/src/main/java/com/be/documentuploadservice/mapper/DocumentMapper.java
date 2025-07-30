package com.be.documentuploadservice.mapper;

import com.be.documentuploadservice.dto.response.UploadResponse;
import com.be.documentuploadservice.entity.Document;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper { // Entity를 DTO로 변환해주는 메소드
  public UploadResponse toUploadResponse(Document document) {
    return UploadResponse.builder()
        .documentId(UUID.randomUUID().toString())
        .status(document.getStatus())
        .message(document.getMessage())
        .s3Key(document.getS3Key())
        .build();
  }

}
