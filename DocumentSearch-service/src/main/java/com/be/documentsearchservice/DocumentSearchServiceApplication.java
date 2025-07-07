package com.be.documentsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@EnableElasticsearchRepositories(basePackages = "com.be.documentsearchservice.repository"

@SpringBootApplication
@ComponentScan(
		//excludeFilters = @ComponentScan.Filter(


		//)
)
public class DocumentSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentSearchServiceApplication.class, args);

	}

}
