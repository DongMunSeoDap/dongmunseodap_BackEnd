package com.be.documentuploadservice.dto.request;

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
public class EventContextInput {
  private String version; // 버전

  private String eventType; // kafka 이벤트 유형

  private String traceId; // 추적 ID

  private String uploadedBy; // 사용자 ID

  @Override
  public String toString() {
    return "EventContextInput{"
        + "version='" + version +
        "', eventType='" + eventType +
        "', traceId='" + traceId +
        "', uploadedBy='" + uploadedBy +
        "'}";
  }

}
