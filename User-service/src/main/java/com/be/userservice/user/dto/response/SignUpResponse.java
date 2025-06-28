package com.be.userservice.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "SignUpResponse DTO", description = "사용자가 회원가입에 대한 응답 반환")
public class SignUpResponse {

  @Schema(description = "회원가입된 사용자 고유 번호", example = "1")
  private Long userId;

  @Schema(description = "회원가입된 사용자 아이디", example = "parkjuyong")
  private String username;

}
