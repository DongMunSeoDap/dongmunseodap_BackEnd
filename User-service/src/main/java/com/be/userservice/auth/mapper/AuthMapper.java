package com.be.userservice.auth.mapper;

import com.be.userservice.auth.dto.response.LoginResponse;
import com.be.userservice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

  public LoginResponse toLoginResponse(User user, String accessToken, Long expirationTime) {
    return LoginResponse.builder()
        .accessToken(accessToken)
        .userId(user.getUserId())
        .username(user.getUsername())
        .name(user.getName())
        .role(user.getRole())
        .expirationTime(expirationTime)
        .build();
  }
}
