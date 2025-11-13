package com.example.apigerman.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaRequestDTO {
    private Long productoId;
    private BigDecimal cantidad;
    private BigDecimal precio;
}
