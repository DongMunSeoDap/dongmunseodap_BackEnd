package com.be.documentsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableElasticsearchRepositories(basePackages = "com.be.documentsearchservice.repository"
@SpringBootApplication
public class DocumentSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentSearchServiceApplication.class, args);
	}

}
