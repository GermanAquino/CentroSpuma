package com.ejemplo.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends AbstractEntity {

    @Column(nullable = false)
    private String nombre;

    @Column
    private String direccion;

    @Column
    private String telefono;

    @Column
    private String ruc;
}