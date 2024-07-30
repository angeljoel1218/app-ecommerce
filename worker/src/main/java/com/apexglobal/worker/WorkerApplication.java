package com.apexglobal.worker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkerApplication.class, args);
	}

	@Value("${api.url.client}")
	private String urlApiCustomer;

	@Value("${api.url.product}")
	private String urlApiProduct;

	@Bean
	public HttpGraphQlClient webClientCustomerBuilder() {
		return HttpGraphQlClient
				.builder(webClientProductBuilder().mutate()
						.baseUrl(urlApiCustomer)
						.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.build())
				.build();
	}

	@Bean
	public WebClient webClientProductBuilder() {
		return WebClient.builder()
				.baseUrl(urlApiProduct)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

}
