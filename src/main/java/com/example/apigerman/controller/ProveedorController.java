package com.example.apigerman.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.apigerman.config.PaginationConfigService;
import com.example.apigerman.dto.ProveedorRequestDTO;
import com.example.apigerman.dto.ProveedorResponseDTO;
import com.example.apigerman.service.ProveedorService;

@Slf4j
@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final PaginationConfigService paginationConfigService;

    // Crear proveedor
    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(@RequestBody ProveedorRequestDTO dto) {
        log.info("Solicitud para crear proveedor: nombre='{}'", dto.getNombre());
        ProveedorResponseDTO response = proveedorService.crearProveedor(dto);
        log.info("Proveedor creado con ID={}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    // Obtener proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtener(@PathVariable Long id) {
        log.info("Solicitud para obtener proveedor con ID={}", id);

        return proveedorService.obtenerPorId(id)
                .map(proveedor -> {
                    log.info("Proveedor encontrado: ID={}, nombre='{}'", proveedor.getId(), proveedor.getNombre());
                    return ResponseEntity.ok(proveedor);
                })
                .orElseGet(() -> {
                    log.warn("Proveedor con ID={} no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Listar todos los proveedores
    @GetMapping
    public List<ProveedorResponseDTO> listarProveedores() {
        log.info("Listando proveedores con paginación por defecto");

        Page<ProveedorResponseDTO> page = proveedorService.listarProveedores(
                null, // sin filtro
                paginationConfigService.defaultPageable());

        log.info("Se obtuvieron {} proveedores", page.getContent().size());

        return page.getContent();
    }

    // Listar proveedores filtrando por nombre (PathVariable)
    @GetMapping("/nombre/{nombre}")
    public List<ProveedorResponseDTO> listarPorNombre(@PathVariable String nombre) {
        log.info("Buscando proveedores filtrados por nombre: '{}'", nombre);

        Page<ProveedorResponseDTO> page = proveedorService.listarProveedores(
                nombre,
                paginationConfigService.defaultPageable());

        log.info("Se encontraron {} proveedores con nombre '{}'",
                page.getContent().size(), nombre);

        return page.getContent();
    }

    // Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ProveedorRequestDTO dto) {

        log.info("Solicitud para actualizar proveedor con ID={}", id);
        ProveedorResponseDTO actualizado = proveedorService.actualizarProveedor(id, dto);
        log.info("Proveedor actualizado: ID={}", actualizado.getId());

        return ResponseEntity.ok(actualizado);
    }

    // Borrar proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        log.info("Solicitud para borrar proveedor con ID={}", id);
        proveedorService.borrarProveedor(id);
        log.info("Proveedor con ID={} eliminado correctamente", id);
        return ResponseEntity.noContent().build();
    }
}
