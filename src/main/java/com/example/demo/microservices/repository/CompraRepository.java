package com.example.demo.microservices.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    Page<Compra> findByProveedorNombreContainingIgnoreCase(String nombreProveedor, Pageable pageable);
}