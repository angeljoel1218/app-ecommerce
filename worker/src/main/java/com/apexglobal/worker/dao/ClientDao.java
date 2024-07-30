package com.apexglobal.worker.dao;

import com.apexglobal.worker.model.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: ClientFallback <br/>
 *
 * @version 1.0.0
 */
@Slf4j
@Component
public class ClientDao {

    @Autowired
    ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @Autowired
    private HttpGraphQlClient webClientCustomerBuilder;

    @Value("${circuitbreaker.instances.name}")
    private String instance;

    public Mono<ClientDto> findById(String id) {
        return reactiveCircuitBreakerFactory.create(instance)
                .run(getCustomer(id), throwable -> Mono.just(ClientDto
                        .builder()
                        .clientId(id)
                        .build()));
    }

    public Mono<ClientDto> getCustomer(String id) {
        String query = """
                query Query($customerId: ID!) {
                  customer(id: $customerId) {
                    _id
                    lastName
                    name
                    address
                    phone
                  }
                }""";
        return webClientCustomerBuilder.document(query)
                .variable("customerId", id)
                .retrieve("customer")
                .toEntity(ClientDto.class)
                .doOnError(e -> {
                    log.info("API NESTjs: {}", e.getLocalizedMessage());
                })
                .doOnNext(v -> {
                    log.info("API NESTjs SUCCESS: {}", v);
                });
    }
}
