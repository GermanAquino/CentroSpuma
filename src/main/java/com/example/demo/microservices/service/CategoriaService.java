package com.example.demo.microservices.service;

import com.example.demo.entities.Categoria;
import com.example.demo.microservices.dto.*;
import com.example.demo.microservices.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Crear una nueva categoría
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto) {
        log.info("Creando nueva categoría con nombre: {}", dto.getNombre());
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre(dto.getNombre());

            Categoria saved = categoriaRepository.save(categoria);
            log.info("Categoría creada exitosamente con ID: {}", saved.getId());
            return mapToResponse(saved);
        } catch (Exception e) {
            log.error("Error al crear la categoría '{}': {}", dto.getNombre(), e.getMessage(), e);
            throw e; // permite que el controller maneje el error si hay global exception handler
        }
    }

    // Listar categorías (búsqueda opcional por nombre)
    public Page<CategoriaResponseDTO> listarCategorias(String nombre, Pageable pageable) {
        log.debug("Listando categorías, filtro nombre='{}', página={}, tamaño={}", nombre, pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Categoria> page;
            if (nombre != null && !nombre.isEmpty()) {
                page = categoriaRepository.findByNombreContainingIgnoreCase(nombre, pageable);
            } else {
                page = categoriaRepository.findAll(pageable);
            }
            log.info("Se encontraron {} categorías en la página {}", page.getNumberOfElements(), pageable.getPageNumber());
            return page.map(this::mapToResponse);
        } catch (Exception e) {
            log.error("Error al listar categorías: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Obtener categoría por ID
    public Optional<CategoriaResponseDTO> obtenerPorId(Long id) {
        log.debug("Buscando categoría por ID: {}", id);
        try {
            Optional<CategoriaResponseDTO> categoria = categoriaRepository.findById(id).map(this::mapToResponse);
            if (categoria.isPresent()) {
                log.info("Categoría encontrada con ID: {}", id);
            } else {
                log.warn("No se encontró la categoría con ID: {}", id);
            }
            return categoria;
        } catch (Exception e) {
            log.error("Error al obtener categoría con ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Actualizar una categoría existente
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO dto) {
        log.info("Actualizando categoría con ID: {}", id);
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Intento de actualizar categoría inexistente con ID: {}", id);
                        return new RuntimeException("Categoría no encontrada");
                    });

            categoria.setNombre(dto.getNombre());
            Categoria updated = categoriaRepository.save(categoria);
            log.info("Categoría actualizada exitosamente con ID: {}", updated.getId());
            return mapToResponse(updated);
        } catch (Exception e) {
            log.error("Error al actualizar categoría con ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Borrar categoría
    public void borrarCategoria(Long id) {
        log.info("Eliminando categoría con ID: {}", id);
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Intento de eliminar categoría inexistente con ID: {}", id);
                        return new RuntimeException("Categoría no encontrada");
                    });
            categoriaRepository.delete(categoria);
            log.info("Categoría eliminada exitosamente con ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar categoría con ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Mapeo entidad → DTO
    private CategoriaResponseDTO mapToResponse(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre()
        );
    }
}
