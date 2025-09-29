package com.be.documentsearchservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@Configuration
@EnableElasticsearchAuditing
@EnableReactiveElasticsearchRepositories(basePackages = "com.be.documentsearchservice.repository")
public class MyClientConfig extends ReactiveElasticsearchConfiguration {


    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withBasicAuth("elastic", "a4Xqk0ehqWPloLpRbrIa") // 👈 계정/비번 꼭 넣기
                //.usingSsl() // 필요하다면 주석 해제
                .build();
    }
}
