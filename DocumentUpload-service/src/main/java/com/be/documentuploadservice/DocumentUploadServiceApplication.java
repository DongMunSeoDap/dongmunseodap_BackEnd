package com.be.documentuploadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//@EnableElasticsearchRepositories(basePackages = "com.be.documentuploadservice.repository")
@SpringBootApplication
public class DocumentUploadServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(DocumentUploadServiceApplication.class, args);
  }

}
