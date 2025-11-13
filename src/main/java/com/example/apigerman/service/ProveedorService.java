package com.example.apigerman.service;

import com.example.apigerman.dto.ProveedorRequestDTO;
import com.example.apigerman.dto.ProveedorResponseDTO;
import com.example.apigerman.repository.ProveedorRepository;
import com.example.demo.entities.Proveedor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorResponseDTO crearProveedor(ProveedorRequestDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(dto.getNombre());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setRuc(dto.getRuc());

        Proveedor saved = proveedorRepository.save(proveedor);
        return mapToResponse(saved);
    }

    public Page<ProveedorResponseDTO> listarProveedores(String nombre, Pageable pageable) {
        return proveedorRepository.findAll(pageable)
                .map(this::mapToResponse);
        // Si quieres filtrar por nombre, luego agrego un findByNombreContainingIgnoreCase
    }

    public Optional<ProveedorResponseDTO> obtenerPorId(Long id) {
        return proveedorRepository.findById(id).map(this::mapToResponse);
    }

    public ProveedorResponseDTO actualizarProveedor(Long id, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedor.setNombre(dto.getNombre());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setRuc(dto.getRuc());

        return mapToResponse(proveedorRepository.save(proveedor));
    }

    public void borrarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedorRepository.delete(proveedor);
    }

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
