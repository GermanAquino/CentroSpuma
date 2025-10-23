package com.example.demo.microservices.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {
    private String nombre;
    private String direccion;
    private String telefono;
    private String ruc;
}
