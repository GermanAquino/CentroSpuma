package com.example.demo.microservices.service;

import com.example.demo.entities.Categoria;
import com.example.demo.microservices.dto.*;
import com.example.demo.microservices.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Crear una nueva categoría
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());

        Categoria saved = categoriaRepository.save(categoria);
        return mapToResponse(saved);
    }

    // Listar categorías (búsqueda opcional por nombre)
    public Page<CategoriaResponseDTO> listarCategorias(String nombre, Pageable pageable) {
        Page<Categoria> page;
        if (nombre != null && !nombre.isEmpty()) {
            page = categoriaRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            page = categoriaRepository.findAll(pageable);
        }
        return page.map(this::mapToResponse);
    }

    // Obtener categoría por ID
    public Optional<CategoriaResponseDTO> obtenerPorId(Long id) {
        return categoriaRepository.findById(id).map(this::mapToResponse);
    }

    // Actualizar una categoría existente
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setNombre(dto.getNombre());

        Categoria updated = categoriaRepository.save(categoria);
        return mapToResponse(updated);
    }

    // Borrar categoría
    public void borrarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoriaRepository.delete(categoria);
    }

    // Mapeo entidad → DTO
    private CategoriaResponseDTO mapToResponse(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre()
        );
    }
}
