package com.apexglobal.worker.dao;

import com.apexglobal.worker.model.ProductDto;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
 private Integer code;
 private String message;
 private ProductDto data;
}
