package com.example.demo.microservices.dto;

import com.example.demo.microservices.dto.DetalleCompraResponseDTO;
import com.example.demo.microservices.dto.ProveedorResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class CompraResponseDTO {
    private Long id;
    private ProveedorResponseDTO proveedor;
    private OffsetDateTime fecha;
    private BigDecimal total;
    private List<DetalleCompraResponseDTO> detalles;
}
