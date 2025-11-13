package com.example.apigerman.controllers;

import com.example.demo.entities.Material;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterialController {

    @GetMapping("/api/material/prueba")
    public Material obtenerMaterialDePrueba() {
        // Crear un material vacío solo para probar la importación
        Material material = new Material();
        material.setNombre("Espuma de prueba");
        material.setDescripcion("Material de ejemplo para verificar importación.");
        return material;
    }
}
