package com.be.documentsearchservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@Configuration
@EnableElasticsearchAuditing
@EnableReactiveElasticsearchRepositories(basePackages = "com.be.documentsearchservice.repository") // ✅ 리액티브 리포지토리 스캔
public class MyClientConfig extends ReactiveElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris:localhost:9200}") // "host:port" 권장
    private String endpoint;

    @Override
    public ClientConfiguration clientConfiguration() {
        String ep = endpoint.replaceFirst("^https?://", ""); // 스킴 제거
        return  ClientConfiguration.builder()
                .connectedTo(ep)
                // .usingSsl()
                // .withBasicAuth("user","pass")
                .build();
    }



}