package com.example.demo.microservices.repository;

import com.example.demo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
