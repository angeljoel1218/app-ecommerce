package com.apexglobal.worker.dao;

import com.apexglobal.worker.model.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class ClientDaoTest {
    @Mock
    private ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @Mock
    private HttpGraphQlClient webClientBuilder;

    @Mock
    GraphQlClient.RequestSpec responseSpec;

    @Mock
    private ReactiveCircuitBreaker reactiveCircuitBreaker;

    @InjectMocks
    private ClientDao clientDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(webClientBuilder.document(anyString())).thenReturn(responseSpec);
        when(responseSpec.variable(anyString(), anyString())).thenReturn(responseSpec);
        when(responseSpec.retrieve(anyString())).thenReturn(mock(GraphQlClient.RetrieveSpec.class));
        when(reactiveCircuitBreakerFactory.create(any())).thenReturn(reactiveCircuitBreaker);
    }

    @Test
    void returnCustomerWhenCallApiId() {
        // Arrange
        String clientId = "123";
        when(responseSpec.retrieve(anyString()).toEntity(ClientDto.class)).thenReturn(Mono.just(ClientDto.builder().clientId(clientId).build()));
        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenAnswer(invocation ->
                ((Mono<ClientDto>) invocation.getArgument(0)).onErrorResume(e ->
                        ((Mono<ClientDto>) invocation.getArgument(1)).thenReturn(ClientDto.builder().clientId(clientId).build())
                )
        );

        // Act
        Mono<ClientDto> result = clientDao.findById(clientId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(client -> client.getClientId().equals(clientId))
                .verifyComplete();
    }

    @Test
    void returnErrorWhenFailCallApi() {
        // Arrange
        String clientId = "123";
        when(responseSpec.retrieve(anyString()).toEntity(ClientDto.class)).thenReturn(Mono.error(new RuntimeException("Service unavailable")));
        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenAnswer(invocation ->
                Mono.just(ClientDto.builder().clientId(clientId).build())
        );

        // Act
        Mono<ClientDto> result = clientDao.findById(clientId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(client -> client.getClientId().equals(clientId))
                .verifyComplete();
    }
}
