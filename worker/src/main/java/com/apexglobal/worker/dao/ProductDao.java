package com.apexglobal.worker.dao;

import com.apexglobal.worker.model.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: ProductFallback <br/>
 *
 * @version 1.0.0
 */
@Slf4j
@Component
public class ProductDao {

    @Autowired
    ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @Autowired
    private WebClient webClientProductBuilder;

    @Value("${circuitbreaker.instances.name}")
    private String instance;

    public Mono<ProductDto> findById(String id) {
        return reactiveCircuitBreakerFactory.create(instance)
                .run(getProduct(id), throwable -> Mono.just(ProductDto
                        .builder()
                        .productId(id)
                        .build()));
    }

    public Mono<ProductDto> getProduct(String id) {
        return webClientProductBuilder
                .get()
                .uri("/product/" + id)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnError(e -> {
                    log.info("API GO: {}", e.getLocalizedMessage());
                })
                .doOnNext(v -> {
                    log.info("API GO SUCCESS: {}", v);
                })
                .map(Response::getData);
    }
}
