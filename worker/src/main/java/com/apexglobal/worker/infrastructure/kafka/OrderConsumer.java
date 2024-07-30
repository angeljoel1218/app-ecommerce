package com.apexglobal.worker.infrastructure.kafka;

import com.apexglobal.worker.business.OrderService;
import com.apexglobal.worker.model.OrderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * <b>Class</b>: OrderConsumer <br/>
 *
 * @version 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

  @Autowired
  private OrderService orderService;

  @KafkaListener(topics = "${kafka.topic.name}")
  public void listener(@Valid @Payload OrderDto orderDto) {
    log.debug("Message received: {}", orderDto);
    orderService.save(orderDto)
            .subscribe();
  }
}
