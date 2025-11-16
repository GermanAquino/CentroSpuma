package com.example.apigerman.controller;

import com.example.apigerman.config.PaginationConfigService;
import com.example.apigerman.dto.*;
import com.example.apigerman.service.VentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final PaginationConfigService paginationConfigService;

    // Crear una nueva venta
    @PostMapping
    public ResponseEntity<VentaResponseDTO> crear(@RequestBody VentaRequestDTO dto) {
        log.info("Creando nueva venta para cliente ID={}", dto.getClienteId());
        VentaResponseDTO response = ventaService.crearVenta(dto);
        log.info("Venta creada con ID={}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtener(@PathVariable Long id) {
        log.info("Buscando venta con ID={}", id);
        return ventaService.obtenerPorId(id)
                .map(v -> {
                    log.info("Venta encontrada: ID={}, Cliente='{}'", v.getId(), v.getClienteNombre());
                    return ResponseEntity.ok(v);
                })
                .orElseGet(() -> {
                    log.warn("Venta con ID={} no encontrada", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Listar todas las ventas
    @GetMapping
    public List<VentaResponseDTO> listarVentas() {
        log.info("Listando todas las ventas con paginación por defecto");

        Page<VentaResponseDTO> page = ventaService.listarVentas(
                null, // sin filtro
                paginationConfigService.defaultPageable());

        log.info("Se obtuvieron {} ventas", page.getContent().size());

        return page.getContent();
    }

    // Listar ventas filtrando por nombre de cliente (PathVariable)
    @GetMapping("/cliente/{nombreCliente}")
    public List<VentaResponseDTO> listarPorCliente(@PathVariable String nombreCliente) {
        log.info("Buscando ventas filtradas por cliente: '{}'", nombreCliente);

        Page<VentaResponseDTO> page = ventaService.listarVentas(
                nombreCliente,
                paginationConfigService.defaultPageable());

        log.info("Se encontraron {} ventas para el cliente '{}'",
                page.getContent().size(), nombreCliente);

        return page.getContent();
    }

    // Actualizar una venta existente
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(@PathVariable Long id, @RequestBody VentaRequestDTO dto) {
        log.info("Actualizando venta con ID={}", id);
        VentaResponseDTO updated = ventaService.actualizarVenta(id, dto);
        log.info("Venta actualizada: ID={}, Cliente='{}'", updated.getId(), updated.getClienteNombre());
        return ResponseEntity.ok(updated);
    }

    // Eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        log.info("Eliminando venta con ID={}", id);
        ventaService.borrarVenta(id);
        log.info("Venta con ID={} eliminada", id);
        return ResponseEntity.noContent().build();
    }
}
