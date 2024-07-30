package com.apexglobal.worker.business.impl;

import com.apexglobal.worker.business.OrderService;
import com.apexglobal.worker.dao.ClientDao;
import com.apexglobal.worker.dao.ProductDao;
import com.apexglobal.worker.infrastructure.repository.OrderDetailRepository;
import com.apexglobal.worker.infrastructure.repository.OrderRepository;
import com.apexglobal.worker.model.ClientDto;
import com.apexglobal.worker.model.DetailOrderDto;
import com.apexglobal.worker.model.OrderDto;
import com.apexglobal.worker.model.entity.DetailOrder;
import com.apexglobal.worker.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <b>Class</b>: OrderServiceImpl <br/>
 *
 * @version 1.0.0
 */
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderDetailRepository orderDetailRepository;

  @Autowired
  ClientDao clientDao;

  @Autowired
  ProductDao productDao;

  @Override
  public Mono<Order> save(OrderDto orderDto) {
    return Mono.just(orderDto)
            .map(this::mapperOrder)
            .flatMap(this::setClient)
            .flatMap(orderRepository::save)
            .doOnNext(o -> saveDetail(orderDto.getDetail(), o.getId())
                    .subscribe());
  }

  private Order mapperOrder(OrderDto orderDto) {
    return Order
            .builder()
            .id(orderDto.getId())
            .orderDate(orderDto.getOrderDate())
            .total(orderDto.getTotal())
            .state(orderDto.getState())
            .client(ClientDto
                    .builder()
                    .clientId(orderDto.getClientId())
                    .build())
            .build();
  }

  public Mono<Order> setClient(Order order) {
    return this.clientDao
            .findById(order.getClient().getClientId())
            .map(c -> {
                c.setClientId(order.getClient().getClientId());
                order.setClient(c);
                return order;
            });
  }

  public Flux<DetailOrder> saveDetail(List<DetailOrderDto> detail, String orderId) {
    return Flux.fromIterable(detail)
            .flatMap(d -> productDao
                    .findById(d.getProductId())
                    .flatMap(r -> {
                      r.setProductId(d.getProductId());
                      return orderDetailRepository
                              .save(DetailOrder
                                      .builder()
                                      .orderId(orderId)
                                      .amount(d.getAmount())
                                      .subtotal(d.getSubtotal())
                                      .product(r)
                                      .build());
                    }));
  }
}
