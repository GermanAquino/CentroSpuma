package com.example.demo.microservices.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private BigDecimal stock;
    private Set<Long> categoriaIds; // IDs de las categorías asociadas
}
