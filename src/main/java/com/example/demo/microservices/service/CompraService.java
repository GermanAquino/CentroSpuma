package com.example.demo.microservices.service;

import com.example.demo.entities.Compra;
import com.example.demo.entities.DetalleCompra;
import com.example.demo.entities.Proveedor;
import com.example.demo.microservices.dto.CompraRequestDTO;
import com.example.demo.microservices.dto.CompraResponseDTO;
import com.example.demo.microservices.dto.DetalleCompraResponseDTO;
import com.example.demo.microservices.dto.ProveedorResponseDTO;
import com.example.demo.microservices.repository.CompraRepository;
import com.example.demo.microservices.repository.DetalleCompraRepository;
import com.example.demo.microservices.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;
    private final DetalleCompraRepository detalleCompraRepository;

    public CompraResponseDTO crearCompra(CompraRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setFecha(dto.getFecha());
        compra.setTotal(dto.getTotal());

        Compra savedCompra = compraRepository.save(compra);

        return mapToResponse(savedCompra);
    }

    public Page<CompraResponseDTO> listarComprasPorProveedor(String nombreProveedor, Pageable pageable) {
        return compraRepository
                .findByProveedorNombreContainingIgnoreCase(nombreProveedor, pageable)
                .map(this::mapToResponse);
    }

    public Page<CompraResponseDTO> listarCompras(Pageable pageable) {
        return compraRepository.findAll(pageable).map(this::mapToResponse);
    }

    public Optional<CompraResponseDTO> obtenerPorId(Long id) {
        return compraRepository.findById(id).map(this::mapToResponse);
    }

    public CompraResponseDTO actualizarCompra(Long id, CompraRequestDTO dto) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        compra.setProveedor(proveedor);
        compra.setFecha(dto.getFecha());
        compra.setTotal(dto.getTotal());

        return mapToResponse(compraRepository.save(compra));
    }

    public void borrarCompra(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        compraRepository.delete(compra);
    }

    private CompraResponseDTO mapToResponse(Compra compra) {
        CompraResponseDTO dto = new CompraResponseDTO();
        dto.setId(compra.getId());

        ProveedorResponseDTO proveedorDTO = new ProveedorResponseDTO();
        proveedorDTO.setId(compra.getProveedor().getId());
        proveedorDTO.setNombre(compra.getProveedor().getNombre());
        proveedorDTO.setDireccion(compra.getProveedor().getDireccion());
        proveedorDTO.setTelefono(compra.getProveedor().getTelefono());
        proveedorDTO.setRuc(compra.getProveedor().getRuc());

        dto.setProveedor(proveedorDTO);
        dto.setFecha(compra.getFecha());
        dto.setTotal(compra.getTotal());

        List<DetalleCompraResponseDTO> detallesDTO = compra.getDetalles()
                .stream()
                .map(detalle -> {
                    DetalleCompraResponseDTO d = new DetalleCompraResponseDTO();
                    d.setId(detalle.getId());
                    d.setProductoId(detalle.getProducto().getId());
                    d.setCantidad(detalle.getCantidad());
                    d.setPrecio(detalle.getPrecio());
                    return d;
                })
                .collect(Collectors.toList());

        dto.setDetalles(detallesDTO);

        return dto;
    }
}
