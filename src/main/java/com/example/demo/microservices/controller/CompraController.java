package com.example.demo.microservices.controller;

import com.example.demo.microservices.dto.CompraRequestDTO;
import com.example.demo.microservices.dto.CompraResponseDTO;
import com.example.demo.microservices.service.CompraService;
import com.example.demo.config.PaginationConfigService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
@Slf4j
public class CompraController {

    private final CompraService compraService;
    private final PaginationConfigService paginationConfigService;

    // CREATE
    @PostMapping
    public ResponseEntity<CompraResponseDTO> crearCompra(@RequestBody CompraRequestDTO dto) {
        log.info("Creando compra para proveedorId={} con total={}", dto.getProveedorId(), dto.getTotal());

        CompraResponseDTO response = compraService.crearCompra(dto);

        log.info("Compra creada con ID={}", response.getId());
        return ResponseEntity.ok(response);
    }

    // LIST PAGINADA
    @GetMapping
    public List<CompraResponseDTO> listarCompras() {
        log.info("Listando todas las compras con paginación por defecto");

        Page<CompraResponseDTO> page = compraService.listarCompras(
                paginationConfigService.defaultPageable());

        log.info("Se obtuvieron {} compras", page.getContent().size());

        return page.getContent();
    }

    @GetMapping("/proveedor/{nombreProveedor}")
    public List<CompraResponseDTO> listarPorProveedor(@PathVariable String nombreProveedor) {
        log.info("Buscando compras filtradas por proveedor: '{}'", nombreProveedor);

        Page<CompraResponseDTO> page = compraService.listarComprasPorProveedor(
                nombreProveedor,
                paginationConfigService.defaultPageable());

        log.info("Se encontraron {} compras para el proveedor '{}'",
                page.getContent().size(), nombreProveedor);

        return page.getContent();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Buscando compra con ID={}", id);

        return compraService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Compra con ID={} no encontrada", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> actualizarCompra(@PathVariable Long id,
            @RequestBody CompraRequestDTO dto) {
        log.info("Actualizando compra ID={} proveedorId={} total={}",
                id, dto.getProveedorId(), dto.getTotal());

        CompraResponseDTO response = compraService.actualizarCompra(id, dto);

        log.info("Compra ID={} actualizada correctamente", id);
        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCompra(@PathVariable Long id) {
        log.info("Borrando compra con ID={}", id);

        compraService.borrarCompra(id);

        log.info("Compra ID={} eliminada correctamente", id);
        return ResponseEntity.noContent().build();
    }
}
