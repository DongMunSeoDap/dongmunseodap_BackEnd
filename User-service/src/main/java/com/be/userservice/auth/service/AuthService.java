package com.be.userservice.auth.service;

import com.be.userservice.auth.dto.request.LoginRequest;
import com.be.userservice.auth.dto.response.LoginResponse;
import com.be.userservice.auth.mapper.AuthMapper;
import com.be.userservice.global.exception.CustomException;
import com.be.userservice.global.jwt.JwtProvider;
import com.be.userservice.user.entity.User;
import com.be.userservice.user.exception.UserErrorCode;
import com.be.userservice.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;
  private final AuthMapper authMapper;

  @Transactional
  public LoginResponse login(LoginRequest loginRequest) {
    User user = userRepository.findByUsername(loginRequest.getUsername())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword());

    // 인증 처리
    authenticationManager.authenticate(authenticationToken);

    // 액세스 토큰 및 리프레시 토큰 발급
    String accessToken = jwtProvider.createAccessToken(user.getUsername());
    String refreshToken = jwtProvider.createRefreshToken(user.getUsername(),
        UUID.randomUUID().toString());

    // 리프레시 토큰 저장
    user.saveRefreshToken(refreshToken);

    // Access Token의 만료 시간을 가져옴
    Long expirationTime = jwtProvider.getExpiration(accessToken);

    // 로그인 성공 로깅
    log.info("로그인 성공: {}", user.getUsername());

    // 로그인 응답 반환
    return authMapper.toLoginResponse(user, accessToken, expirationTime);
  }
}