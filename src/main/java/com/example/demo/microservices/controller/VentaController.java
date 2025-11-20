package com.example.demo.microservices.controller;

import com.example.demo.microservices.dto.*;
import com.example.demo.microservices.service.VentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {
    
    private final VentaService ventaService;

    // Crear una nueva venta
    @PostMapping
    public ResponseEntity<VentaResponseDTO> crear(@RequestBody VentaRequestDTO dto) {
        log.info("[POST] Creando nueva venta para cliente ID: {}", dto.getClienteId());
        try {
            VentaResponseDTO nuevaVenta = ventaService.crearVenta(dto);
            log.info("Venta creada correctamente con ID: {}", nuevaVenta.getId());
            return ResponseEntity.status(201).body(nuevaVenta);
        } catch (Exception e) {
            log.error("Error al crear venta para cliente ID {}", dto.getClienteId(), e);
            throw e;
        }
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtener(@PathVariable Long id) {
        log.info("[GET] Solicitando venta con ID: {}", id);
        return ventaService.obtenerPorId(id)
            .map(venta -> {
                log.info("Venta encontrada: ID {} - Cliente: {}", venta.getId(), venta.getClienteNombre());
                return ResponseEntity.ok(venta);
            })
            .orElseGet(() -> {
                log.warn("Venta con ID {} no encontrada", id);
                return ResponseEntity.notFound().build();
            });
    }

    // Listar ventas (con búsqueda opcional por nombre de cliente)
    @GetMapping
    public Page<VentaResponseDTO> listar(
        @RequestParam(defaultValue = "") String nombreCliente,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        log.info("[GET] Listando ventas (cliente='{}', página={}, tamaño={})", nombreCliente, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<VentaResponseDTO> ventas = ventaService.listarVentas(nombreCliente, pageable);
        log.info("Total de ventas encontradas: {}", ventas.getTotalElements());
        return ventas;
    }

    // Actualizar una venta existente
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(@PathVariable Long id, @RequestBody VentaRequestDTO dto) {
        log.info("[PUT] Actualizando venta con ID: {}", id);
        try {
            VentaResponseDTO actualizada = ventaService.actualizarVenta(id, dto);
            log.info("Venta actualizada correctamente con ID: {}", id);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            log.error("Error al actualizar venta con ID {}", id, e);
            throw e;
        }
    }

    // Eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        log.warn("[DELETE] Solicitando eliminación de venta con ID: {}", id);
        try {
            ventaService.borrarVenta(id);
            log.info("Venta con ID {} eliminada correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error al eliminar venta con ID {}", id, e);
            throw e;
        }
    }
}
