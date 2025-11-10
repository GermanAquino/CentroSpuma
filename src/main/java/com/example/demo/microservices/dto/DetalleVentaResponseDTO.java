package com.example.demo.microservices.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaResponseDTO {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private BigDecimal cantidad;
    private BigDecimal precio;
}