package com.example.demo.microservices.controller;

import com.example.demo.microservices.dto.ProveedorRequestDTO;
import com.example.demo.microservices.dto.ProveedorResponseDTO;
import com.example.demo.microservices.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(@RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.status(201).body(proveedorService.crearProveedor(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtener(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Page<ProveedorResponseDTO> listar(
        @RequestParam(defaultValue = "") String nombre,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return proveedorService.listarProveedores(nombre, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(@PathVariable Long id, @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.ok(proveedorService.actualizarProveedor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        proveedorService.borrarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
