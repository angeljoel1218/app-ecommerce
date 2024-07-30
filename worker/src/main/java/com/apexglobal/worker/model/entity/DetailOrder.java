package com.apexglobal.worker.model.entity;

import com.apexglobal.worker.model.ProductDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collation = "detailOrder")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrder {
  @Id
  private String id;
  private Integer amount;
  private BigDecimal subtotal;
  private ProductDto product;
  private String orderId;
}
