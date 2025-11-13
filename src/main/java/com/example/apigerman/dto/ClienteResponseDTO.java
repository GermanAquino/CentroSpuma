package com.example.apigerman.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ruc;
}
