package com.be.documentuploadservice.exception;

import com.be.documentuploadservice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum KafkaErrorCode implements BaseErrorCode {

  // Producer 관련 에러
  MESSAGE_PUBLISH_FAILED("KAFKA_001", "메시지 발행에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCER_CONNECTION_FAILED("KAFKA_002", "Kafka Producer 연결에 실패했습니다", HttpStatus.SERVICE_UNAVAILABLE),
  PRODUCER_TIMEOUT("KAFKA_003", "메시지 발행 중 타임아웃이 발생했습니다", HttpStatus.REQUEST_TIMEOUT),
  PRODUCER_INTERRUPTED("KAFKA_004", "메시지 발행이 중단되었습니다", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCER_SERIALIZATION_ERROR("KAFKA_005", "메시지 직렬화에 실패했습니다", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;

}
