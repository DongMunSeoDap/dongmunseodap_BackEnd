package com.be.gatewayservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @PostMapping("/query")
    public Mono<ResponseEntity<String>> handleQueryFallback() {
        return Mono.just(ResponseEntity.ok("⚠️ DocumentSearch 서비스에 일시적 문제가 발생했습니다. 나중에 다시 시도해주세요."));
    }

    @PostMapping("/external")
    public Mono<ResponseEntity<String>> handleUploadFallback() {
        return Mono.just(ResponseEntity.ok("⚠️ DocumentUpload 서비스에 일시적 문제가 발생했습니다. 나중에 다시 시도해주세요."));
    }

    @PostMapping("/auth")
    public Mono<ResponseEntity<String>> handleAuthFallback() {
        return Mono.just(ResponseEntity.ok("⚠️ 인증 서비스가 현재 응답하지 않습니다. 나중에 다시 로그인해주세요."));
    }
}
