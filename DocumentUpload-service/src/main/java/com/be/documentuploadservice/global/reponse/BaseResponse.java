package com.be.documentuploadservice.global.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

  private boolean success; // 요청 성공 여부

  private int code; // Http 상태 코드

  private String message; // 응답 메세지

  private T data; // 응답 데이터

  public static <T> BaseResponse<T> success(String message, T data) {
    return new BaseResponse<>(true, 200, message, data);
  }

  public static <T> BaseResponse<T> error(int code, String message) {
    return new BaseResponse<>(false, code, message, null);
  }

}
