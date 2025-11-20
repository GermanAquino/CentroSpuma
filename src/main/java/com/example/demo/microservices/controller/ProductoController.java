package com.example.demo.microservices.controller;

import com.example.demo.microservices.dto.*;
import com.example.demo.microservices.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        log.info("[POST] Creando nuevo producto: {}", dto.getNombre());
        try {
            ProductoResponseDTO nuevo = productoService.crearProducto(dto);
            log.info("Producto creado correctamente con ID: {}", nuevo.getId());
            return ResponseEntity.status(201).body(nuevo);
        } catch (Exception e) {
            log.error("Error al crear producto: {}", dto.getNombre(), e);
            throw e;
        }
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(@PathVariable Long id) {
        log.info("[GET] Solicitando producto con ID: {}", id);
        return productoService.obtenerPorId(id)
                .map(producto -> {
                    log.info("Producto encontrado: {}", producto.getNombre());
                    return ResponseEntity.ok(producto);
                })
                .orElseGet(() -> {
                    log.warn("Producto con ID {} no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Listar productos (con búsqueda opcional por nombre)
    @GetMapping
    public List<ProductoResponseDTO> listar(@RequestParam(defaultValue = "") String nombre) {
        log.info("[GET] Listando productos con paginación por defecto (nombre='{}')", nombre);

        Page<ProductoResponseDTO> page = productoService.listarProductos(
                nombre,
                paginationConfigService.defaultPageable());

        log.info("Total de productos obtenidos: {}", page.getContent().size());

        return page.getContent();
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @RequestBody ProductoRequestDTO dto) {
        log.info("[PUT] Actualizando producto con ID: {} - Nuevo nombre: {}", id, dto.getNombre());
        try {
            ProductoResponseDTO actualizado = productoService.actualizarProducto(id, dto);
            log.info("Producto actualizado correctamente con ID: {}", id);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            log.error("Error al actualizar producto con ID {}", id, e);
            throw e;
        }
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        log.warn("[DELETE] Solicitando eliminación del producto con ID: {}", id);
        try {
            productoService.borrarProducto(id);
            log.info("Producto con ID {} eliminado correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error al eliminar producto con ID {}", id, e);
            throw e;
        }
    }
}
