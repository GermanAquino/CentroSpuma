package com.example.demo.microservices.controller;

import com.example.demo.microservices.config.PaginationConfigService;
import com.example.demo.microservices.dto.ProveedorRequestDTO;
import com.example.demo.microservices.dto.ProveedorResponseDTO;
import com.example.demo.microservices.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
@Slf4j
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final PaginationConfigService paginationConfigService;

    // CREAR
    @PostMapping
    public ProveedorResponseDTO crear(@RequestBody ProveedorRequestDTO dto) {
        log.info("[POST] Creando proveedor: nombre='{}', RUC='{}'", dto.getNombre(), dto.getRuc());
        ProveedorResponseDTO response = proveedorService.crearProveedor(dto);
        log.info("Proveedor creado con ID={}", response.getId());
        return response;
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ProveedorResponseDTO obtener(@PathVariable Long id) {
        log.info("[GET] Buscando proveedor con ID={}", id);
        return proveedorService.obtenerPorId(id)
                .map(proveedor -> {
                    log.info("Proveedor encontrado: ID={}, nombre='{}'", proveedor.getId(), proveedor.getNombre());
                    return proveedor;
                })
                .orElseGet(() -> {
                    log.warn("Proveedor con ID={} no encontrado", id);
                    return null; // podrías lanzar excepción o retornar ResponseEntity.notFound()
                });
    }

    // Listar todos los proveedores
    @GetMapping
    public List<ProveedorResponseDTO> listarProveedores() {
        log.info("Listando proveedores con paginación por defecto");

        Page<ProveedorResponseDTO> page = proveedorService.listarProveedores(
                null, // sin filtro
                paginationConfigService.defaultPageable());

        log.info("Se obtuvieron {} proveedores", page.getContent().size());

        return page.getContent();
    }

    // Listar proveedores filtrando por nombre (PathVariable)
    @GetMapping("/nombre/{nombre}")
    public List<ProveedorResponseDTO> listarPorNombre(@PathVariable String nombre) {
        log.info("Buscando proveedores filtrados por nombre: '{}'", nombre);

        Page<ProveedorResponseDTO> page = proveedorService.listarProveedores(
                nombre,
                paginationConfigService.defaultPageable());

        log.info("Se encontraron {} proveedores con nombre '{}'",
                page.getContent().size(), nombre);

        return page.getContent();
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ProveedorResponseDTO actualizar(@PathVariable Long id, @RequestBody ProveedorRequestDTO dto) {
        log.info("[PUT] Actualizando proveedor ID={} con nombre='{}', RUC='{}'", id, dto.getNombre(), dto.getRuc());
        ProveedorResponseDTO response = proveedorService.actualizarProveedor(id, dto);
        log.info("Proveedor actualizado: ID={}, nombre='{}'", response.getId(), response.getNombre());
        return response;
    }

    // BORRAR
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        log.info("[DELETE] Borrando proveedor con ID={}", id);
        proveedorService.borrarProveedor(id);
        log.info("Proveedor ID={} eliminado correctamente", id);
    }
}
