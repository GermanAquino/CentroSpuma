package com.example.apigerman.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleCompraRequestDTO {
    private Long productoId;
    private BigDecimal cantidad;
    private BigDecimal precio;
}
