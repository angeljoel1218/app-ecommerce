package com.apexglobal.worker.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * <b>Class</b>: ProductDto <br/>
 *
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
  @NotNull
  private String productId;
  private String description;
  private BigDecimal price;
  private String category;
}
