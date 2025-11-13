package com.example.apigerman.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilRequestDTO {
    private String telefono;
    private String direccion;
    private String avatarUrl;
}
