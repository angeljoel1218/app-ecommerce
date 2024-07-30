package com.apexglobal.worker.business;

import com.apexglobal.worker.model.OrderDto;
import com.apexglobal.worker.model.entity.Order;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: OrderService <br/>
 *
 * @version 1.0.0
 */
public interface OrderService {
  Mono<Order> save(OrderDto orderDto);
}
