package com.apexglobal.worker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
  @NotNull
  @JsonProperty("code")
  private String id;

  @NotNull
  @JsonProperty("oderDate")
  @Pattern(regexp = "^(19|2[0-9])[0-9]{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])((\\\\+|-)[0-1][0-9]{3})?$")
  private LocalDate orderDate;

  @NotNull
  @DecimalMin("1")
  @JsonProperty("total")
  private BigDecimal total;

  @NotNull
  @JsonProperty("state")
  private String state;

  @NotNull
  @JsonProperty("clientId")
  private String clientId;

  @NotNull
  @Valid
  @JsonProperty("detail")
  private List<DetailOrderDto> detail;
}
