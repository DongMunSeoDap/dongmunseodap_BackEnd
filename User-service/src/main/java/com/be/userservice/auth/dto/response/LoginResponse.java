package com.be.userservice.auth.dto.response;

import com.be.userservice.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LoginResponse DTO", description = "사용자 로그인에 대한 응답 반환")
public class LoginResponse {

  @Schema(description = "사용자 Access Token")
  private String accessToken;

  @Schema(description = "사용자 ID", example = "1")
  private Long userId;

  @Schema(description = "사용자 아이디 또는 이메일", example = "parkjuyong")
  private String username;

  @Schema(description = "사용자 별명", example = "주용쓰")
  private String name;

  @Schema(description = "사용자 권한", example = "USER")
  private Role role;

  @Schema(description = "사용자 Access Token 만료 시간", example = "1000000")
  private Long expirationTime;
}
