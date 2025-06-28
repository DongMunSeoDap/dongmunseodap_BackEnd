package com.be.documentsearchservice.consumer;

import com.be.documentsearchservice.kafka.avro.DocumentUploadedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer { // 범용 메시지 처리 ( 모든 메세지 용 )

    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    ) // 카프카로부터 수신한 메세지 처리

    public void onMessage(ConsumerRecord<String, DocumentUploadedEvent> record) {
        // 이곳은 추후 이벤트 타입별 분기 처리를 고려한 통합 처리 영역
        String eventType = record.value().getEventType();

        switch (eventType) {
            case "DOCUMENT_UPLOADED":
                handleDocumentUploaded(record.value());
                break;
            default:
                System.out.println("⚠️ Unknown event type: " + eventType);
        }
    }

    // DOCUMENT_UPLOADED 이벤트를 처리하는 메소드 현제는 파일명만 출력하게끔 설정했으나 추후 DB저장, 파싱 등 구체적인 처리는 여기서 구현
    private void handleDocumentUploaded(DocumentUploadedEvent event) {
        System.out.println("[KafkaMessageConsumer] Document uploaded: " + event.getPayload().getFileName());
    }
}

