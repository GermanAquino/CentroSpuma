package com.example.apigerman.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.apigerman.config.PaginationConfigService;
import com.example.apigerman.dto.ProductoRequestDTO;
import com.example.apigerman.dto.ProductoResponseDTO;
import com.example.apigerman.service.ProductoService;

@Slf4j
@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final PaginationConfigService paginationConfigService;

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@RequestBody ProductoRequestDTO dto) {
        log.info("Solicitud para crear producto con nombre='{}'", dto.getNombre());
        log.debug("Payload recibido: {}", dto);

        ProductoResponseDTO response = productoService.crearProducto(dto);

        log.info("Producto creado con ID={}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(@PathVariable Long id) {
        log.info("Solicitud para obtener producto con ID={}", id);

        return productoService.obtenerPorId(id)
                .map(producto -> {
                    log.info("Producto encontrado: ID={}, nombre={}", producto.getId(), producto.getNombre());
                    return ResponseEntity.ok(producto);
                })
                .orElseGet(() -> {
                    log.warn("Producto con ID={} no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Listar todos los productos
    @GetMapping
    public List<ProductoResponseDTO> listarProductos() {
        log.info("Listando productos con paginación por defecto");

        Page<ProductoResponseDTO> page = productoService.listarProductos(
                null, // sin filtro de nombre
                paginationConfigService.defaultPageable());

        log.info("Se obtuvieron {} productos", page.getContent().size());
        return page.getContent();
    }

    // Listar productos filtrando por nombre (PathVariable)
    @GetMapping("/nombre/{nombre}")
    public List<ProductoResponseDTO> listarPorNombre(@PathVariable String nombre) {
        log.info("Buscando productos filtrados por nombre: '{}'", nombre);

        Page<ProductoResponseDTO> page = productoService.listarProductos(
                nombre,
                paginationConfigService.defaultPageable() // Asumiendo que también lo tienes aquí
        );

        log.info("Se encontraron {} productos con nombre '{}'", page.getContent().size(), nombre);
        return page.getContent();
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @RequestBody ProductoRequestDTO dto) {
        log.info("Solicitud para actualizar producto con ID={}", id);
        log.debug("Payload recibido: {}", dto);

        ProductoResponseDTO response = productoService.actualizarProducto(id, dto);

        log.info("Producto con ID={} actualizado correctamente", id);
        return ResponseEntity.ok(response);
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        log.info("Solicitud para eliminar producto con ID={}", id);

        productoService.borrarProducto(id);

        log.info("Producto con ID={} eliminado correctamente", id);
        return ResponseEntity.noContent().build();
    }
}
