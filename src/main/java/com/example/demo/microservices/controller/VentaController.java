package com.example.demo.microservices.controller;

import com.example.demo.microservices.dto.*;
import com.example.demo.microservices.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {
    
    private final VentaService ventaService;

    // Crear una nueva venta
    @PostMapping
    public ResponseEntity<VentaResponseDTO> crear(@RequestBody VentaRequestDTO dto) {
        return ResponseEntity.status(201).body(ventaService.crearVenta(dto));
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtener(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Listar ventas (con búsqueda opcional por nombre de cliente)
    @GetMapping
    public Page<VentaResponseDTO> listar(
        @RequestParam(defaultValue = "") String nombreCliente,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ventaService.listarVentas(nombreCliente, pageable);
    }

    // Actualizar una venta existente
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(@PathVariable Long id, @RequestBody VentaRequestDTO dto) {
        return ResponseEntity.ok(ventaService.actualizarVenta(id, dto));
    }

    // Eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        ventaService.borrarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
