package com.apexglobal.worker.infrastructure.kafka;

import com.apexglobal.worker.business.OrderService;
import com.apexglobal.worker.model.OrderDto;
import com.apexglobal.worker.model.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class OrderConsumerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderConsumer orderConsumer;

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldProcessMessageWhenNewMessageArrives() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        orderDto.setId("123");
        orderDto.setOrderDate(LocalDate.now());
        orderDto.setTotal(new BigDecimal("100.00"));
        orderDto.setState("paid");

        when(orderService.save(any(OrderDto.class))).thenReturn(Mono.just(new Order()));

        // Act
        orderConsumer.listener(orderDto);

        // Assert
        verify(orderService).save(any(OrderDto.class));
    }
}
