package com.example.apigerman.service;

import com.example.apigerman.dto.MaterialRequestDTO;
import com.example.apigerman.dto.MaterialResponseDTO;
import com.example.apigerman.exception.ResourceNotFoundException;
import com.example.apigerman.repository.MaterialRepository;
import com.example.demo.entities.Material;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    // Crear un nuevo material
    public MaterialResponseDTO crearMaterial(MaterialRequestDTO dto) {
        log.info("Creando nuevo material con nombre='{}'", dto.getNombre());
        log.debug("Payload recibido: {}", dto);

        Material material = new Material();
        material.setNombre(dto.getNombre());
        material.setDescripcion(dto.getDescripcion());

        Material saved = materialRepository.save(material);

        log.info("Material creado con ID={}", saved.getId());
        return mapToResponse(saved);
    }

    // Listar materiales (búsqueda opcional por nombre)
    public Page<MaterialResponseDTO> listarMateriales(String nombre, Pageable pageable) {
        log.info("Listando materiales. Filtro nombre='{}', página={}, tamaño={}",
                nombre, pageable.getPageNumber(), pageable.getPageSize());

        Page<Material> page;

        if (nombre != null && !nombre.isEmpty()) {
            log.debug("Ejecutando búsqueda por nombre");
            page = materialRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            log.debug("Listando todos los materiales sin filtro");
            page = materialRepository.findAll(pageable);
        }

        log.info("Total elementos encontrados: {}", page.getTotalElements());
        return page.map(this::mapToResponse);
    }

    // Obtener material por ID — versión usada internamente
    public MaterialResponseDTO obtenerPorIdOrThrow(Long id) {
        log.info("Buscando material con ID={} en la base de datos", id);

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Material con ID={} no encontrado", id);
                    return new ResourceNotFoundException("Material no encontrado con ID=" + id);
                });

        log.info("Material encontrado: ID={}, nombre={}", material.getId(), material.getNombre());
        return mapToResponse(material);
    }

    // Método antiguo que devolvía Optional
    public java.util.Optional<MaterialResponseDTO> obtenerPorId(Long id) {
        log.debug("Obteniendo material (versión antigua Optional) ID={}", id);
        return materialRepository.findById(id).map(this::mapToResponse);
    }

    // Actualizar un material existente
    public MaterialResponseDTO actualizarMaterial(Long id, MaterialRequestDTO dto) {
        log.info("Actualizando material con ID={}", id);
        log.debug("Payload recibido para actualizar: {}", dto);

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de actualizar material inexistente ID={}", id);
                    return new ResourceNotFoundException("Material no encontrado con ID=" + id);
                });

        material.setNombre(dto.getNombre());
        material.setDescripcion(dto.getDescripcion());

        Material updated = materialRepository.save(material);

        log.info("Material actualizado: ID={}", updated.getId());
        return mapToResponse(updated);
    }

    // Borrar material
    public void borrarMaterial(Long id) {
        log.info("Eliminando material con ID={}", id);

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de borrar material inexistente ID={}", id);
                    return new ResourceNotFoundException("Material no encontrado con ID=" + id);
                });

        materialRepository.delete(material);
        log.info("Material con ID={} eliminado correctamente", id);
    }

    // Mapeo entidad → DTO
    private MaterialResponseDTO mapToResponse(Material material) {
        log.debug("Mapeando entidad Material a DTO. ID={}", material.getId());
        return new MaterialResponseDTO(
                material.getId(),
                material.getNombre(),
                material.getDescripcion()
        );
    }
}