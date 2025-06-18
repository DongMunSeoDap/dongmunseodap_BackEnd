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
public class DocumentMetaInput { // 파일 메타 정보

  private String mimeType;

  private String language;

  @Override
  public String toString() {
    return "DocumentMetaInput{mimeType='" + mimeType + "', language='" + language + "'}";
  }
}
