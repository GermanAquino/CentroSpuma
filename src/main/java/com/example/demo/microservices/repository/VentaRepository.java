package com.example.demo.microservices.repository;

import com.example.demo.entities.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Buscar por nombre del cliente (si quisieras una búsqueda más avanzada)
    Page<Venta> findByCliente_NombreContainingIgnoreCase(String nombre, Pageable pageable);
}
