package com.apexglobal.worker.infrastructure.repository;

import com.apexglobal.worker.model.entity.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <b>Class</b>: OrderRepository <br/>
 *
 * @version 1.0.0
 */
@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}
