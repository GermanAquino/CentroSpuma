package com.example.demo.microservices.controller;

import com.example.demo.microservices.dto.*;
import com.example.demo.microservices.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import com.example.demo.config.PaginationConfigService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final PaginationConfigService paginationConfigService;

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(@RequestBody CategoriaRequestDTO dto) {
        log.info("Recibida solicitud para crear categoría: nombre='{}'", dto.getNombre());

        CategoriaResponseDTO creada = categoriaService.crearCategoria(dto);

        log.info("Categoría creada exitosamente con id={}", creada.getId());
        return ResponseEntity.status(201).body(creada);
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtener(@PathVariable Long id) {
        log.info("Buscando categoría con id={}", id);

        return categoriaService.obtenerPorId(id)
                .map(dto -> {
                    log.info("Categoría encontrada: id={} nombre='{}'", dto.getId(), dto.getNombre());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> {
                    log.warn("No se encontró categoría con id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Listar todas las categorías (sin filtro)
    @GetMapping
    public List<CategoriaResponseDTO> listarCategorias() {
        log.info("Listando categorías con paginación por defecto (sin filtro)");

        Page<CategoriaResponseDTO> page = categoriaService.listarCategorias(
                null, // Sin filtro
                paginationConfigService.defaultPageable());

        log.info("Se obtuvieron {} categorías", page.getContent().size());

        return page.getContent();
    }

    // Listar categorías filtrando por nombre (PathVariable)
    @GetMapping("/nombre/{nombre}")
    public List<CategoriaResponseDTO> listarCategoriasPorNombre(@PathVariable String nombre) {
        log.info("Buscando categorías filtradas por nombre='{}'", nombre);

        Page<CategoriaResponseDTO> page = categoriaService.listarCategorias(
                nombre,
                paginationConfigService.defaultPageable());

        log.info("Se encontraron {} categorías con nombre='{}'",
                page.getContent().size(), nombre);

        return page.getContent();
    }

    // Actualizar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id,
                                                           @RequestBody CategoriaRequestDTO dto) {

        log.info("Actualizando categoría id={} con nombre='{}'", id, dto.getNombre());

        CategoriaResponseDTO actualizada = categoriaService.actualizarCategoria(id, dto);

        log.info("Categoría actualizada exitosamente id={} nombre='{}'",
                actualizada.getId(), actualizada.getNombre());

        return ResponseEntity.ok(actualizada);
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar categoría id={}", id);

        categoriaService.borrarCategoria(id);

        log.info("Categoría eliminada exitosamente id={}", id);

        return ResponseEntity.noContent().build();
    }
}
