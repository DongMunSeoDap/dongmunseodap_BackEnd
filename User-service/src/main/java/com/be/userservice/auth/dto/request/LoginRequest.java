package com.be.userservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "LoginRequest DTO", description = "사용자 로그인을 위한 데이터 전송")
public class LoginRequest {

  @NotBlank(message = "사용자 아이디 항목은 필수입니다.")
  @Schema(description = "사용자 아이디", example = "parkjuyong")
  private String username;

  @NotBlank(message = "비밀번호 항목은 필수입니다.")
  @Schema(description = "비밀번호", example = "1234")
  private String password;

}
