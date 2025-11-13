package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria extends AbstractEntity {

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;
    
    @ManyToMany(mappedBy = "categorias")
    private Set<Producto> productos = new HashSet<>();
}