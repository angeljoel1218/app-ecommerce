package com.apexglobal.worker.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderDto {
  @NotNull
  @DecimalMin("1")
  private Integer amount;

  @NotNull
  @DecimalMin("1")
  private BigDecimal subtotal;

  @NotNull
  private String productId;
}
