package com.apexglobal.worker.model.entity;

import com.apexglobal.worker.model.ClientDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collation = "order")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  @Id
  private String id;
  private LocalDate orderDate;
  private BigDecimal total;
  private String state;
  private ClientDto client;
}
