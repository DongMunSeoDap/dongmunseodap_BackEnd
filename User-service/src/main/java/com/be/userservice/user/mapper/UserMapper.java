package com.be.userservice.user.mapper;

import com.be.userservice.user.dto.response.SignUpResponse;
import com.be.userservice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public SignUpResponse tosignUpResponse(User user) {
    return SignUpResponse.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .build();
  }
}
