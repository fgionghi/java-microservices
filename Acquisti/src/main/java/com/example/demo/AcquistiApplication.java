package com.example.demo;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableSwagger2
public class AcquistiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AcquistiApplication.class, args);
	}
	
	//Documentazione swagger
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/**"))
				.build()
				.apiInfo(new ApiInfo(
						 "TITLE",
						 "DESCRIPTION",
						 "VERSION",
						 "TERMS OF SERVICE URL",
						 new Contact("NAME", "URL", "EMAIL"),
						 "LICENSE",
						 "LICENSE URL",
						 Collections.emptyList()
						 ));
	}
}
