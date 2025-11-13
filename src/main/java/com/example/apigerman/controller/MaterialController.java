package com.example.apigerman.controller;

import com.example.apigerman.config.PaginationConfigService;
import com.example.apigerman.dto.MaterialRequestDTO;
import com.example.apigerman.dto.MaterialResponseDTO;
import com.example.apigerman.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;
    private final PaginationConfigService paginationConfigService;

    // Crear un nuevo material
    @PostMapping
    public MaterialResponseDTO crearMaterial(@RequestBody MaterialRequestDTO dto) {
        return materialService.crearMaterial(dto);
    }

    // Listar todos los materiales
    @GetMapping
    public List<MaterialResponseDTO> listarMateriales() {
        Page<MaterialResponseDTO> page = materialService.listarMateriales(
                null,
                paginationConfigService.defaultPageable()
        );
        return page.getContent();
    }

    // Listar filtrando por nombre (PathVariable)
    @GetMapping("/nombre/{nombre}")
    public List<MaterialResponseDTO> listarPorNombre(@PathVariable String nombre) {
        Page<MaterialResponseDTO> page = materialService.listarMateriales(
                nombre,
                paginationConfigService.defaultPageable()
        );
        return page.getContent();
    }

    // Obtener material por ID
    @GetMapping("/{id}")
    public MaterialResponseDTO obtenerPorId(@PathVariable Long id) {
        return materialService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));
    }

    // Actualizar material existente
    @PutMapping("/{id}")
    public MaterialResponseDTO actualizarMaterial(@PathVariable Long id, @RequestBody MaterialRequestDTO dto) {
        return materialService.actualizarMaterial(id, dto);
    }

    // Eliminar material
    @DeleteMapping("/{id}")
    public void borrarMaterial(@PathVariable Long id) {
        materialService.borrarMaterial(id);
    }
}
