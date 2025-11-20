package com.example.demo.microservices.controller;

import com.example.demo.microservices.dto.*;
import com.example.demo.microservices.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final PaginationConfigService paginationConfigService;

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(@RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(201).body(categoriaService.crearCategoria(dto));
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtener(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar categorías (con búsqueda opcional por nombre)
    @GetMapping
    public List<CategoriaResponseDTO> listar(
            @RequestParam(defaultValue = "") String nombre) {
        log.info("Listando categorías con paginación por defecto y filtro nombre='{}'", nombre);

        Page<CategoriaResponseDTO> page = categoriaService.listarCategorias(
                nombre,
                paginationConfigService.defaultPageable());

        log.info("Se obtuvieron {} categorías", page.getContent().size());

        return page.getContent();
    }

    // Actualizar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id,
            @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, dto));
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        categoriaService.borrarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
