package com.be.userservice.global.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 설정 클래스임을 나타내는 어노테이션(이 클래스에서 등록된 빈 객체는 스프링 컨테이너에서 관리된다)
public class SwaggerConfig {

  @Value("${server.servlet.context-path:}") // @Value : 민감한 정보 관리 (환경변수 없을 시 기본 포트사용)
  // :의 의미 -> yml이나 properties를 설정하지 않았면 기본값을 사용하겠다는 의미
  // :가 없다면? -> yml나 properties를 꼭 설정해야한다
  private String contextPath;

  @Bean
  public OpenAPI coustomOpenAPI() { // OpenAPI : Swagger의 핵심 객체로, API 문서의 메타정보를 정의합니다.
    Server localServer = new Server();
    localServer.setUrl(contextPath);
    localServer.setDescription("local Server");

    return new OpenAPI() // 서버 등록 메서드
        /*.addServersItem(localServer) // 로컬 서버 말고 베포 서버를 만듦
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // 보안 아이템 설정(value)
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))*/
        .info(new Info().title("Swagger API 명세서")
            .version("1.0")
            .description("사용설명서 챗봇 빌더"));
  }

  @Bean
  public GroupedOpenApi customGroupedOpenApi() {
    return GroupedOpenApi.builder()
        .group("api")
        .pathsToMatch("/**")
        .build();
  }
}

