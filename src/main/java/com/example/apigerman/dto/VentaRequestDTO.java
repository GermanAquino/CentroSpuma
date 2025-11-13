package com.example.apigerman.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {
    private Long clienteId;
    private OffsetDateTime fecha;
    private BigDecimal total;
    private List<DetalleVentaRequestDTO> detalles;
}
