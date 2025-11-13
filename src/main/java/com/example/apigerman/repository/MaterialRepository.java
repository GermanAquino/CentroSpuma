package com.example.apigerman.repository;

import com.example.demo.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
