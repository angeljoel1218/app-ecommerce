package com.apexglobal.worker.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * <b>Class</b>: ClientDto <br/>
 *
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
  @NotNull
  private String clientId;
  private String name;
  private String lastName;
  private String phone;
  private String address;
}
