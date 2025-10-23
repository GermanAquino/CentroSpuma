package com.example.demo.microservices.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class CompraRequestDTO {
    private Long proveedorId;
    private OffsetDateTime fecha;
    private BigDecimal total;
    private List<Long> detallesIds; // alternativamente se puede usar DetalleCompraRequestDTO directamente
}
