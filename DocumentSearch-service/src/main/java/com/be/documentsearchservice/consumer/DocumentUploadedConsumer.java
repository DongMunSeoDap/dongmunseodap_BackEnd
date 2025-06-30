package com.be.documentsearchservice.consumer;

import com.be.documentsearchservice.dto.DocumentUploadedEvent;
import com.be.documentsearchservice.dto.DocumentUploadedEvent.Payload;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// Kafka에서 발행한 DocumentUploadedEvent를 수신하는 Consumer Component class
@Component
public class DocumentUploadedConsumer {
    // 로거 객체를 생성, 로그 기록에 사용
    private static final Logger logger = LoggerFactory.getLogger(DocumentUploadedConsumer.class);

    // 카프카 리스너로 동작하는 메소드
    @KafkaListener( // kafka 메시지를 자동 수신하도록 설정
            topics = "${kafka.topic.name}", // application.properties에 정의된 토픽명 사용
            groupId = "${spring.kafka.consumer.group-id}", // 동일한 그룹 ID를 가진 컨슈머끼리 메세지를 공유
            containerFactory = "kafkaListenerContainerFactory" // 지정한 리스너 컨테이너 팩토리 사용
    )

    // 메시지를 받을 메서드, ConsumerRecord 객체는 메시지의 전체 정보를 포함.
    public void consume(ConsumerRecord<String, DocumentUploadedEvent> record) {
        DocumentUploadedEvent event = record.value(); // 메시지 본문(value)만 추출

        if (event == null) { // null-safe처리 : 만약 메시지가 비어있다면 처리 중단, 경고 로그 남김.
            logger.warn("[DocumentUploadedConsumer] Received null event. Skipping.");
            return;
        }

        logger.info("[DocumentUploadedConsumer] Received event");

        // 이벤트 메타 정보 출력 : 버전, 타입, 트레이스 ID 등 기본 정보 출력
        logger.info("Version: {}", event.getVersion());
        logger.info("Event Type: {}", event.getEvent_type());
        logger.info("Trace ID: {}", event.getTrace_id());

        Payload payload = event.getPayload(); // payload 객체 추출, 문서 파일의 주요 정보가 담긴 구조
        if (payload != null) { // payload 객체 추출 null-safe
            logger.info("Document ID: {}", payload.getDocument_id());
            logger.info("File Name: {}", payload.getFile_name());
            logger.info("S3 Path: {}", payload.getS3_path());
            logger.info("Uploaded By: {}", payload.getUploaded_at());
            logger.info("Uploaded At: {}", payload.getUploaded_by());
        } else {
            logger.warn("Payload is null");
        }

        // meta 필드 관련 null-safe및 MIME 타입 ( 파일 형식) 과 언어 정보 출력
        if (event.getMeta() != null) {
            logger.info("Mime Type: {}", event.getMeta().getMime_type());
            logger.info("Language: {}", event.getMeta().getLanguage());
        } else {
            logger.warn("Meta is null");
        }
    }
}
