package com.example.apigerman.service;

import com.example.apigerman.dto.DetalleCompraRequestDTO;
import com.example.apigerman.dto.DetalleCompraResponseDTO;
import com.example.apigerman.repository.CompraRepository;
import com.example.apigerman.repository.DetalleCompraRepository;
import com.example.apigerman.repository.ProductoRepository;
import com.example.demo.entities.Compra;
import com.example.demo.entities.DetalleCompra;
import com.example.demo.entities.Producto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetalleCompraService {

    private final DetalleCompraRepository detalleCompraRepository;
    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;

    public DetalleCompraResponseDTO crearDetalle(DetalleCompraRequestDTO dto, Long compraId) {
        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetalleCompra detalle = new DetalleCompra();
        detalle.setCompra(compra);
        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecio(dto.getPrecio());

        DetalleCompra saved = detalleCompraRepository.save(detalle);
        return mapToResponse(saved);
    }

    public Page<DetalleCompraResponseDTO> listarDetalles(Pageable pageable) {
        return detalleCompraRepository.findAll(pageable).map(this::mapToResponse);
    }

    public Optional<DetalleCompraResponseDTO> obtenerPorId(Long id) {
        return detalleCompraRepository.findById(id).map(this::mapToResponse);
    }

    public DetalleCompraResponseDTO actualizarDetalle(Long id, DetalleCompraRequestDTO dto) {
        DetalleCompra detalle = detalleCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DetalleCompra no encontrado"));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecio(dto.getPrecio());

        return mapToResponse(detalleCompraRepository.save(detalle));
    }

    public void borrarDetalle(Long id) {
        DetalleCompra detalle = detalleCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DetalleCompra no encontrado"));

        detalleCompraRepository.delete(detalle);
    }

    private DetalleCompraResponseDTO mapToResponse(DetalleCompra d) {
        DetalleCompraResponseDTO dto = new DetalleCompraResponseDTO();
        dto.setId(d.getId());
        dto.setProductoId(d.getProducto().getId());
        dto.setCantidad(d.getCantidad());
        dto.setPrecio(d.getPrecio());
        return dto;
    }
}
