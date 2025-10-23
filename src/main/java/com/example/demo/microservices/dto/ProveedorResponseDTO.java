package com.example.demo.microservices.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ruc;
}
