package com.example.apigerman.service;

import com.example.apigerman.dto.DetalleVentaResponseDTO;
import com.example.apigerman.dto.VentaRequestDTO;
import com.example.apigerman.dto.VentaResponseDTO;
import com.example.apigerman.repository.ClienteRepository;
import com.example.apigerman.repository.DetalleVentaRepository;
import com.example.apigerman.repository.ProductoRepository;
import com.example.apigerman.repository.VentaRepository;
import com.example.demo.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    // Crear una venta con sus detalles
    public VentaResponseDTO crearVenta(VentaRequestDTO dto) {
        log.info("Creando venta para cliente ID={}", dto.getClienteId());

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> {
                    log.warn("Cliente no encontrado con ID={}", dto.getClienteId());
                    return new RuntimeException("Cliente no encontrado");
                });

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(dto.getFecha());
        venta.setTotal(dto.getTotal());

        // Guardamos primero la venta para tener un ID
        Venta savedVenta = ventaRepository.save(venta);
        log.info("Venta creada con ID={}", savedVenta.getId());

        // Creamos los detalles
        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            List<DetalleVenta> detalles = dto.getDetalles().stream()
                    .map(d -> {
                        Producto producto = productoRepository.findById(d.getProductoId())
                                .orElseThrow(() -> {
                                    log.warn("Producto no encontrado con ID={}", d.getProductoId());
                                    return new RuntimeException("Producto no encontrado");
                                });

                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setVenta(savedVenta);
                        detalle.setProducto(producto);
                        detalle.setCantidad(d.getCantidad());
                        detalle.setPrecio(d.getPrecio());
                        log.info("Detalle agregado: Producto ID={}, Cantidad={}, Precio={}",
                                producto.getId(), d.getCantidad(), d.getPrecio());
                        return detalle;
                    })
                    .collect(Collectors.toList());

            detalleVentaRepository.saveAll(detalles);
            savedVenta.setDetalles(detalles);
            log.info("Se agregaron {} detalles a la venta ID={}", detalles.size(), savedVenta.getId());
        }

        return mapToResponse(savedVenta);
    }

    // Listar ventas con búsqueda por nombre de cliente
    public Page<VentaResponseDTO> listarVentas(String nombreCliente, Pageable pageable) {
        log.info("Listando ventas{}",
                nombreCliente != null && !nombreCliente.isEmpty() ? " filtradas por cliente: " + nombreCliente : "");

        Page<Venta> page;

        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            page = ventaRepository.findByCliente_NombreContainingIgnoreCase(nombreCliente, pageable);
        } else {
            page = ventaRepository.findAll(pageable);
        }

        log.info("Se encontraron {} ventas", page.getContent().size());
        return page.map(this::mapToResponse);
    }

    // Obtener venta por ID
    public Optional<VentaResponseDTO> obtenerPorId(Long id) {
        log.info("Buscando venta con ID={}", id);
        return ventaRepository.findById(id)
                .map(v -> {
                    log.info("Venta encontrada: ID={}, Cliente='{}'", v.getId(), v.getCliente().getNombre());
                    return mapToResponse(v);
                });
    }

    // Actualizar venta (cliente, total y detalles)
    public VentaResponseDTO actualizarVenta(Long id, VentaRequestDTO dto) {
        log.info("Actualizando venta ID={}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Venta no encontrada con ID={}", id);
                    return new RuntimeException("Venta no encontrada");
                });

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> {
                    log.warn("Cliente no encontrado con ID={}", dto.getClienteId());
                    return new RuntimeException("Cliente no encontrado");
                });

        venta.setCliente(cliente);
        venta.setFecha(dto.getFecha());
        venta.setTotal(dto.getTotal());

        // Actualizamos los detalles (se eliminan y se vuelven a crear)
        detalleVentaRepository.deleteAll(venta.getDetalles());
        log.info("Se eliminaron los detalles anteriores de la venta ID={}", venta.getId());
        venta.getDetalles().clear();

        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            List<DetalleVenta> nuevosDetalles = dto.getDetalles().stream()
                    .map(d -> {
                        Producto producto = productoRepository.findById(d.getProductoId())
                                .orElseThrow(() -> {
                                    log.warn("Producto no encontrado con ID={}", d.getProductoId());
                                    return new RuntimeException("Producto no encontrado");
                                });

                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setVenta(venta);
                        detalle.setProducto(producto);
                        detalle.setCantidad(d.getCantidad());
                        detalle.setPrecio(d.getPrecio());
                        log.info("Detalle actualizado: Producto ID={}, Cantidad={}, Precio={}",
                                producto.getId(), d.getCantidad(), d.getPrecio());
                        return detalle;
                    })
                    .collect(Collectors.toList());

            detalleVentaRepository.saveAll(nuevosDetalles);
            venta.setDetalles(nuevosDetalles);
            log.info("Se agregaron {} nuevos detalles a la venta ID={}", nuevosDetalles.size(), venta.getId());
        }

        Venta updated = ventaRepository.save(venta);
        log.info("Venta actualizada: ID={}, Cliente='{}'", updated.getId(), updated.getCliente().getNombre());

        return mapToResponse(updated);
    }

    // Borrar venta y sus detalles
    public void borrarVenta(Long id) {
        log.info("Eliminando venta con ID={}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Venta no encontrada con ID={}", id);
                    return new RuntimeException("Venta no encontrada");
                });

        ventaRepository.delete(venta);
        log.info("Venta eliminada: ID={}", id);
    }

    private VentaResponseDTO mapToResponse(Venta venta) {
        List<DetalleVentaResponseDTO> detallesDTO = venta.getDetalles().stream()
                .map(d -> new DetalleVentaResponseDTO(
                        d.getId(),
                        d.getProducto().getId(),
                        d.getProducto().getNombre(),
                        d.getCantidad(),
                        d.getPrecio()
                ))
                .collect(Collectors.toList());

        return new VentaResponseDTO(
                venta.getId(),
                venta.getCliente().getId(),
                venta.getCliente().getNombre(),
                venta.getCliente().getRuc(),
                venta.getFecha(),
                venta.getTotal(),
                detallesDTO
        );
    }
}