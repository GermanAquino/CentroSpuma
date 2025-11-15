package com.example.apigerman.controller;

import com.example.apigerman.config.PaginationConfigService;
import com.example.apigerman.dto.MaterialRequestDTO;
import com.example.apigerman.dto.MaterialResponseDTO;
import com.example.apigerman.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;
    private final PaginationConfigService paginationConfigService;

    // Crear un nuevo material
    @PostMapping
    public MaterialResponseDTO crearMaterial(@RequestBody MaterialRequestDTO dto) {
        log.info("Solicitud recibida para crear un material: nombre='{}'", dto.getNombre());
        MaterialResponseDTO creado = materialService.crearMaterial(dto);
        log.info("Material creado exitosamente con ID={}", creado.getId());
        return creado;
    }

    // Listar todos los materiales
    @GetMapping
    public List<MaterialResponseDTO> listarMateriales() {
        log.info("Listando materiales con paginación por defecto");
        Page<MaterialResponseDTO> page = materialService.listarMateriales(
                null,
                paginationConfigService.defaultPageable()
        );
        log.info("Se obtuvieron {} materiales", page.getContent().size());
        return page.getContent();
    }

    // Listar filtrando por nombre (PathVariable)
    @GetMapping("/nombre/{nombre}")
    public List<MaterialResponseDTO> listarPorNombre(@PathVariable String nombre) {
        log.info("Buscando materiales filtrados por nombre: '{}'", nombre);
        Page<MaterialResponseDTO> page = materialService.listarMateriales(
                nombre,
                paginationConfigService.defaultPageable()
        );
        log.info("Se encontraron {} materiales con nombre '{}'", page.getContent().size(), nombre);
        return page.getContent();
    }

    // Obtener material por ID
    @GetMapping("/{id}")
    public MaterialResponseDTO obtenerPorId(@PathVariable Long id) {
        log.info("Buscando material con ID={}", id);
        return materialService.obtenerPorId(id)
                .map(material -> {
                    log.info("Material encontrado: ID={}, nombre={}", material.getId(), material.getNombre());
                    return material;
                })
                .orElseThrow(() -> {
                    log.warn("Material con ID={} no encontrado", id);
                    return new RuntimeException("Material no encontrado");
                });
    }

    // Actualizar material existente
    @PutMapping("/{id}")
    public MaterialResponseDTO actualizarMaterial(@PathVariable Long id, @RequestBody MaterialRequestDTO dto) {
        log.info("Solicitud para actualizar material ID={}. Nuevos valores: nombre='{}'", id, dto.getNombre());
        MaterialResponseDTO actualizado = materialService.actualizarMaterial(id, dto);
        log.info("Material ID={} actualizado exitosamente", id);
        return actualizado;
    }

    // Eliminar material
    @DeleteMapping("/{id}")
    public void borrarMaterial(@PathVariable Long id) {
        log.info("Solicitud para eliminar material ID={}", id);
        materialService.borrarMaterial(id);
        log.info("Material ID={} eliminado exitosamente", id);
    }
}
