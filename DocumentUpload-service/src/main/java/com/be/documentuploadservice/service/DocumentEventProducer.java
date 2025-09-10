package com.be.documentuploadservice.service;

import com.be.documentuploadservice.DocumentUploadedEvent;
import com.be.documentuploadservice.Meta;
import com.be.documentuploadservice.Payload;
import com.be.documentuploadservice.entity.UploadedFile;
import com.be.documentuploadservice.exception.KafkaErrorCode;
import com.be.documentuploadservice.exception.S3ErrorCode;
import com.be.documentuploadservice.global.exception.CustomException;
import java.time.ZoneId;

import com.be.documentuploadservice.repository.FileElasticSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentEventProducer {

  // Producer 역할을 간단하게 해주는 템플릿 객체
  private final KafkaTemplate<String, DocumentUploadedEvent> kafkaTemplate;
  private final FileElasticSearchRepository fileElasticSearchRepository;

  public void publishDocumentEvent(String fileId, String s3Key, String traceId, String mimeType) {

    try {

        UploadedFile fileEntity = fileElasticSearchRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(S3ErrorCode.FILE_NOT_FOUND));

      // Meta 객체 생성
      Meta meta = Meta.newBuilder()
          .setMimeType(mimeType != null ? mimeType : "application/pdf")
          .setLanguage("ko") // 언어 감지 로직 우선 생략
          .build();

      // Payload 객체 생성
      Payload payload = Payload.newBuilder()
          .setDocumentId(fileEntity.getFileId()) // ES에 저장된 파일 ID 사용
          .setFileName(fileEntity.getFileName()) // 원본 파일 이름이 정의되어 있음
          .setS3Path(s3Key) // s3Key
          .setUploadedBy(fileEntity.getUploadedBy()) // 추후 사용자 이름으로 변경(현재 "admin"으로 고정됨)
          .setUploadedAt(fileEntity.getUploadedAt()
              .atZone(ZoneId.of("Asia/Seoul"))
              .toInstant())
          .build();

      // DocumentUploadedEvent 객체 생성
      DocumentUploadedEvent event = DocumentUploadedEvent.newBuilder()
          .setVersion("1.0")
          .setEventType("DOCUMENT_UPLOADED")
          .setTraceId(traceId)
          .setPayload(payload)
          .setMeta(meta)
          .build();

      // Kafka로 이벤트 발행
      kafkaTemplate.send("document-uploaded", traceId, event)
          .whenComplete((result, ex) -> {
            if (ex == null) {
              log.info("이벤트 발행 성공: traceId={}, fileId={}", traceId, fileEntity.getFileId());
            } else {
              log.error("이벤트 발행 실패: traceId={}, fileId={}", traceId, fileEntity.getFileId(), ex);
            }
          });

    } catch (Exception e) {
      log.error("Kafka 이벤트 발행 중 오류 발생: traceId={}", traceId, e);
      throw new CustomException(KafkaErrorCode.MESSAGE_PUBLISH_FAILED);
    }
  }
}
