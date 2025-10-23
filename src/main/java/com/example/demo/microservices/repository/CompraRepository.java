package com.example.demo.microservices.repository;

import com.example.demo.entities.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}