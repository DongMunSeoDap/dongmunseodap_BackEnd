package com.be.documentsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

//@EnableElasticsearchRepositories(basePackages = "com.be.documentsearchservice.repository")
@SpringBootApplication(
		exclude = {
				KafkaAutoConfiguration.class
		}
)
public class DocumentSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentSearchServiceApplication.class, args);
	}

}
