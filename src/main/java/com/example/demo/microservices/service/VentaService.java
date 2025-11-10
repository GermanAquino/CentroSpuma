package com.example.demo.microservices.service;

import com.example.demo.microservices.dto.*;
import com.example.demo.entities.*;
import com.example.demo.microservices.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    // Crear una venta con sus detalles
    public VentaResponseDTO crearVenta(VentaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(dto.getFecha());
        venta.setTotal(dto.getTotal());

        // Guardamos primero la venta para tener un ID
        Venta savedVenta = ventaRepository.save(venta);

        // Creamos los detalles
        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            List<DetalleVenta> detalles = dto.getDetalles().stream()
                    .map(d -> {
                        Producto producto = productoRepository.findById(d.getProductoId())
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setVenta(savedVenta);
                        detalle.setProducto(producto);
                        detalle.setCantidad(d.getCantidad());
                        detalle.setPrecio(d.getPrecio());
                        return detalle;
                    })
                    .collect(Collectors.toList());

            detalleVentaRepository.saveAll(detalles);
            savedVenta.setDetalles(detalles);
        }

        return mapToResponse(savedVenta);
    }

    // Listar ventas con búsqueda por nombre de cliente
    public Page<VentaResponseDTO> listarVentas(String nombreCliente, Pageable pageable) {
        Page<Venta> page;

        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            page = ventaRepository.findByCliente_NombreContainingIgnoreCase(nombreCliente, pageable);
        } else {
            page = ventaRepository.findAll(pageable);
        }

        return page.map(this::mapToResponse);
    }

    // Obtener venta por id
    public Optional<VentaResponseDTO> obtenerPorId(Long id) {
        return ventaRepository.findById(id).map(this::mapToResponse);
    }

    // Actualizar venta (cliente, total y detalles)
    public VentaResponseDTO actualizarVenta(Long id, VentaRequestDTO dto) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        venta.setCliente(cliente);
        venta.setFecha(dto.getFecha());
        venta.setTotal(dto.getTotal());

        // Actualizamos los detalles (se eliminan y se vuelven a crear)
        detalleVentaRepository.deleteAll(venta.getDetalles());
        venta.getDetalles().clear();

        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            List<DetalleVenta> nuevosDetalles = dto.getDetalles().stream()
                    .map(d -> {
                        Producto producto = productoRepository.findById(d.getProductoId())
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setVenta(venta);
                        detalle.setProducto(producto);
                        detalle.setCantidad(d.getCantidad());
                        detalle.setPrecio(d.getPrecio());
                        return detalle;
                    })
                    .collect(Collectors.toList());

            detalleVentaRepository.saveAll(nuevosDetalles);
            venta.setDetalles(nuevosDetalles);
        }

        return mapToResponse(ventaRepository.save(venta));
    }

    // Borrar venta y sus detalles
    public void borrarVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        ventaRepository.delete(venta);
    }

    private VentaResponseDTO mapToResponse(Venta venta) {
        List<DetalleVentaResponseDTO> detallesDTO = venta.getDetalles().stream()
                .map(d -> new DetalleVentaResponseDTO(
                        d.getId(),
                        d.getProducto().getId(),
                        d.getProducto().getNombre(), // agregamos nombre del producto
                        d.getCantidad(),
                        d.getPrecio()
                ))
                .collect(Collectors.toList());

        return new VentaResponseDTO(
                venta.getId(),
                venta.getCliente().getId(),
                venta.getCliente().getNombre(), // agregamos nombre del cliente
                venta.getCliente().getRuc(),    // agregamos RUC del cliente
                venta.getFecha(),
                venta.getTotal(),
                detallesDTO
        );
    }
}
