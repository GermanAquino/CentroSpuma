package com.example.demo.microservices.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private RolResponseDTO rol;
    private PerfilResponseDTO perfil;
}
