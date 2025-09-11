package com.be.documentuploadservice.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories // Elasticsearch Repository 활성화를 위한 어노테이션
public class ElasticSearchConfig extends ElasticsearchConfiguration {

  /*@Value("${spring.elasticsearch.username}")
  private String username;

  @Value("${spring.elasticsearch.password}")
  private String password;*/

  /*@Value("${spring.elasticsearch.uris}")
  private String esHost;*/

  @Override
  public ClientConfiguration clientConfiguration() {
    return ClientConfiguration.builder()
        .connectedTo("localhost:9200")
        // .withBasicAuth(username, password)
        .build();
  }
}
