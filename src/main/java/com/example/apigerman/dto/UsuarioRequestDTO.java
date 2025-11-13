package com.example.apigerman.dto;

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
