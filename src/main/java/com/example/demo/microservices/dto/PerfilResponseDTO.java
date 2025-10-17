package com.example.demo.microservices.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilResponseDTO {
    private Long id;
    private String telefono;
    private String direccion;
    private String avatarUrl;
}
