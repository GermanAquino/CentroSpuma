package com.ejemplo.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios = new ArrayList<>();
}