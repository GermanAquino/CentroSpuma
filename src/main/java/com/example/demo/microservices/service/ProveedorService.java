package com.example.demo.microservices.service;

import com.example.demo.entities.Proveedor;
import com.example.demo.microservices.dto.ProveedorRequestDTO;
import com.example.demo.microservices.dto.ProveedorResponseDTO;
import com.example.demo.microservices.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    // Crear proveedor
    public ProveedorResponseDTO crearProveedor(ProveedorRequestDTO dto) {
        log.info("Creando proveedor: {}", dto.getNombre());
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(dto.getNombre());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setRuc(dto.getRuc());

        Proveedor saved = proveedorRepository.save(proveedor);
        log.info("Proveedor creado con ID={}", saved.getId());
        return mapToResponse(saved);
    }

    // Listar proveedores con filtro opcional por nombre
    public Page<ProveedorResponseDTO> listarProveedores(String nombre, Pageable pageable) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            log.info("Buscando proveedores filtrando por nombre: '{}'", nombre);
            Page<ProveedorResponseDTO> page = proveedorRepository
                    .findByNombreContainingIgnoreCase(nombre, pageable)
                    .map(this::mapToResponse);
            log.info("Se encontraron {} proveedores con nombre '{}'", page.getContent().size(), nombre);
            return page;
        }

        log.info("Listando todos los proveedores con paginación por defecto");
        Page<ProveedorResponseDTO> page = proveedorRepository
                .findAll(pageable)
                .map(this::mapToResponse);
        log.info("Se obtuvieron {} proveedores", page.getContent().size());
        return page;
    }

    // Obtener proveedor por ID
    public Optional<ProveedorResponseDTO> obtenerPorId(Long id) {
        log.info("Buscando proveedor con ID={}", id);
        Optional<ProveedorResponseDTO> optional = proveedorRepository.findById(id).map(this::mapToResponse);
        if (optional.isEmpty()) {
            log.warn("Proveedor con ID={} no encontrado", id);
        }
        return optional;
    }

    // Actualizar proveedor
    public ProveedorResponseDTO actualizarProveedor(Long id, ProveedorRequestDTO dto) {
        log.info("Actualizando proveedor con ID={}", id);
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Proveedor con ID={} no encontrado para actualización", id);
                    return new RuntimeException("Proveedor no encontrado");
                });

        proveedor.setNombre(dto.getNombre());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setRuc(dto.getRuc());

        Proveedor updated = proveedorRepository.save(proveedor);
        log.info("Proveedor con ID={} actualizado", updated.getId());
        return mapToResponse(updated);
    }

    // Borrar proveedor
    public void borrarProveedor(Long id) {
        log.info("Eliminando proveedor con ID={}", id);
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Proveedor con ID={} no encontrado para borrado", id);
                    return new RuntimeException("Proveedor no encontrado");
                });
        proveedorRepository.delete(proveedor);
        log.info("Proveedor con ID={} eliminado", id);
    }

    // Mapeo entidad -> DTO
    private ProveedorResponseDTO mapToResponse(Proveedor p) {
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDireccion(p.getDireccion());
        dto.setTelefono(p.getTelefono());
        dto.setRuc(p.getRuc());
        return dto;
    }
}