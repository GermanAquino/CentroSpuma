package com.example.apigerman.service;

import com.example.apigerman.dto.MaterialRequestDTO;
import com.example.apigerman.dto.MaterialResponseDTO;
import com.example.apigerman.repository.MaterialRepository;
import com.example.demo.entities.Material;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    // Crear un nuevo material
    public MaterialResponseDTO crearMaterial(MaterialRequestDTO dto) {
        Material material = new Material();
        material.setNombre(dto.getNombre());
        material.setDescripcion(dto.getDescripcion());

        Material saved = materialRepository.save(material);
        return mapToResponse(saved);
    }

    // Listar materiales (búsqueda opcional por nombre)
    public Page<MaterialResponseDTO> listarMateriales(String nombre, Pageable pageable) {
        Page<Material> page;
        if (nombre != null && !nombre.isEmpty()) {
            page = materialRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            page = materialRepository.findAll(pageable);
        }
        return page.map(this::mapToResponse);
    }

    // Obtener material por ID
    public Optional<MaterialResponseDTO> obtenerPorId(Long id) {
        return materialRepository.findById(id).map(this::mapToResponse);
    }

    // Actualizar un material existente
    public MaterialResponseDTO actualizarMaterial(Long id, MaterialRequestDTO dto) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        material.setNombre(dto.getNombre());
        material.setDescripcion(dto.getDescripcion());

        Material updated = materialRepository.save(material);
        return mapToResponse(updated);
    }

    // Borrar material
    public void borrarMaterial(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));
        materialRepository.delete(material);
    }

    // Mapeo entidad → DTO
    private MaterialResponseDTO mapToResponse(Material material) {
        return new MaterialResponseDTO(
                material.getId(),
                material.getNombre(),
                material.getDescripcion()
        );
    }
}
