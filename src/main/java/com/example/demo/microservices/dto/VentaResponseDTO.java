package com.example.demo.microservices.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String clienteRuc;
    private OffsetDateTime fecha;
    private BigDecimal total;
    private List<DetalleVentaResponseDTO> detalles;
}
