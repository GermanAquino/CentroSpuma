package com.example.demo.microservices.repository;

import com.example.demo.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar productos por nombre (contiene, sin distinción de mayúsculas)
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Buscar productos por categoría
    Page<Producto> findByCategorias_Id(Long categoriaId, Pageable pageable);
}
