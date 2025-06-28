package com.be.userservice.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Role {
  @Schema(description = "회원")
  MEMBER,
  @Schema(description = "비회원")
  NONMEMBER,
  @Schema(description = "관리자")
  ADMIN,
  @Schema(description = "개발자")
  DEVELOPER;

}
