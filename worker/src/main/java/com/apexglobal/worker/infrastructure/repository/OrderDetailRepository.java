package com.apexglobal.worker.infrastructure.repository;

import com.apexglobal.worker.model.entity.DetailOrder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <b>Class</b>: OrderDetailRepository <br/>
 *
 * @version 1.0.0
 */
@Repository
public interface OrderDetailRepository  extends ReactiveMongoRepository<DetailOrder, String> {
}
