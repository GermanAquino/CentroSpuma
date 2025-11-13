package com.example.apigerman.repository;

import com.example.demo.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
