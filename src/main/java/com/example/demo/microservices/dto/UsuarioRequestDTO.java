package com.example.demo.microservices.dto;

import com.ejemplo.demo.dto.PerfilRequestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {
    private String nombre;
    private String email;
    private String contrasena;
    private Long rolId;
    private PerfilRequestDTO perfil;
}
