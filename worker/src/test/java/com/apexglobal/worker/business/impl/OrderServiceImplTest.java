package com.apexglobal.worker.business.impl;

import com.apexglobal.worker.dao.ClientDao;
import com.apexglobal.worker.dao.ProductDao;
import com.apexglobal.worker.infrastructure.repository.OrderDetailRepository;
import com.apexglobal.worker.infrastructure.repository.OrderRepository;
import com.apexglobal.worker.model.ClientDto;
import com.apexglobal.worker.model.DetailOrderDto;
import com.apexglobal.worker.model.OrderDto;
import com.apexglobal.worker.model.ProductDto;
import com.apexglobal.worker.model.entity.DetailOrder;
import com.apexglobal.worker.model.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private ClientDao clientDao;

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCompleteSaveWhenAddNewOrder() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        orderDto.setId("1");
        orderDto.setOrderDate(LocalDate.now());
        orderDto.setTotal(new BigDecimal("100.0"));
        orderDto.setState("NEW");
        orderDto.setDetail(Collections.singletonList(new DetailOrderDto(1, new BigDecimal("2.00"), "826d5897-d100-4c5a-8a21-c5519c706f72")));

        Order order = new Order();
        order.setId("1");

        ClientDto clientDto = new ClientDto();
        clientDto.setClientId("1");

        DetailOrder detailOrder = new DetailOrder();
        detailOrder.setOrderId("1");
        detailOrder.setAmount(2);
        detailOrder.setSubtotal(new BigDecimal("100.0"));

        when(clientDao.findById(any())).thenReturn(Mono.just(clientDto));
        when(productDao.findById(any())).thenReturn(Mono.just(new ProductDto()));
        when(orderRepository.save(any(Order.class))).thenReturn(Mono.just(order));
        when(orderDetailRepository.save(any(DetailOrder.class))).thenReturn(Mono.just(detailOrder));

        // Act
        Mono<Order> result = orderService.save(orderDto);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(savedOrder -> savedOrder.getId().equals("1"))
                .verifyComplete();
    }
}
