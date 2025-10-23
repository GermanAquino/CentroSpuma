package com.example.demo.microservices.repository;

import com.example.demo.entities.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
}