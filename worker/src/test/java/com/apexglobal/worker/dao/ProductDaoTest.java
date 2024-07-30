package com.apexglobal.worker.dao;

import com.apexglobal.worker.model.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ProductDaoTest {
    @Mock
    private ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private ReactiveCircuitBreaker reactiveCircuitBreaker;

    @InjectMocks
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.mutate()).thenReturn(webClientBuilder);
        when(webClient.mutate().baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(reactiveCircuitBreakerFactory.create(any())).thenReturn(reactiveCircuitBreaker);
    }

    @Test
    void returnClientWhenSendIdProduct() {
        // Arrange
        String productId = "456";
        ProductDto expectedProduct = ProductDto.builder().productId(productId).build();
        Response rep = Response.builder().data(expectedProduct).build();

        when(responseSpec.bodyToMono(Response.class)).thenReturn(Mono.just(rep));
        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenAnswer(invocation ->
                ((Mono<ProductDto>) invocation.getArgument(0)).onErrorResume(e ->
                        ((Mono<ProductDto>) invocation.getArgument(1)).thenReturn(new ProductDto())
                )
        );

        // Act
        Mono<ProductDto> result = productDao.findById(productId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(product -> product.getProductId().equals(productId))
                .verifyComplete();
    }

    @Test
    void returnErrorWhenFailCallApi() {
        // Arrange
        String productId = "456";
        when(responseSpec.bodyToMono(Response.class)).thenReturn(Mono.error(() -> new Exception("Error server")));
        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenAnswer(invocation ->
                ((Mono<ProductDto>) invocation.getArgument(0)).onErrorResume(e ->
                        Mono.just(ProductDto.builder().productId("456").build())
                )
        );

        // Act
        Mono<ProductDto> result = productDao.findById(productId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(product -> product.getProductId().equals(productId))
                .verifyComplete();
    }
}
