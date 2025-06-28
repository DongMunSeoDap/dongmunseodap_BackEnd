package com.be.userservice.global.exception;

import com.be.userservice.global.exception.model.BaseErrorCode;
import com.be.userservice.global.response.BaseResponse;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 이 클래스는 ExceptionHandler 임을 의미
public class GlobalExceptionHandler {

  // 커스텀 예외
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
    BaseErrorCode errorCode = ex.getErrorCode();
    log.error("Custom 오류 발생: {}", ex.getMessage());
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(BaseResponse.error(errorCode.getStatus().value(), ex.getMessage()));
  }

  // Validation 실패
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse<Object>> handleValidationException(
      MethodArgumentNotValidException ex) {
    String errorMessages =
        ex.getBindingResult().getFieldErrors().stream()
            .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
            .collect(Collectors.joining(" / "));
    log.warn("Validation 오류 발생: {}", errorMessages);
    return ResponseEntity.badRequest().body(BaseResponse.error(400, errorMessages));
  }

  // 예상치 못한 예외
  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<Object>> handleException(Exception ex) {
    log.error("Server 오류 발생: ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error(500, "예상치 못한 서버 오류가 발생했습니다."));
  }

  // 지원하지 않는 Http 메서드로 요청한 경우
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<BaseResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
    log.warn("지원하지 않는 HTTP 메서드: {}", ex.getMethod());
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(BaseResponse.error(405, "지원하지 않는 HTTP 메서드입니다."));
  }

  // 요청 본문이 맞지 않은 경우
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<BaseResponse<Object>> handleNotReadable(HttpMessageNotReadableException ex) {
    log.warn("요청 본문을 읽을 수 없습니다: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(BaseResponse.error(400, "요청 형식이 잘못되었습니다."));
  }

  // 필수 요청 파라미터가 누락 되었을 때
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<BaseResponse<Object>> handleMissingParams(
      MissingServletRequestParameterException ex) {
    String message = String.format("필수 파라미터 누락: %s", ex.getParameterName());
    log.warn(message);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(BaseResponse.error(400, message));
  }
}
