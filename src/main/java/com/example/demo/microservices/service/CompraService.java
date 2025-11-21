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
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;
    private final DetalleCompraRepository detalleCompraRepository;

    // --------------------------------------------------------
    // CREAR COMPRA
    // --------------------------------------------------------
    public CompraResponseDTO crearCompra(CompraRequestDTO dto) {
        log.info("Creando nueva compra para proveedorId={} fecha={} total={}",
                dto.getProveedorId(), dto.getFecha(), dto.getTotal());

        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado con id={}", dto.getProveedorId());
                    return new RuntimeException("Proveedor no encontrado");
                });

        log.info("Proveedor encontrado: id={} nombre={}", proveedor.getId(), proveedor.getNombre());

        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setFecha(dto.getFecha());
        compra.setTotal(dto.getTotal());

        Compra savedCompra = compraRepository.save(compra);

        log.info("Compra creada exitosamente con id={}", savedCompra.getId());

        return mapToResponse(savedCompra);
    }

    // --------------------------------------------------------
    // LISTAR POR PROVEEDOR (filtro nombre)
    // --------------------------------------------------------
    public Page<CompraResponseDTO> listarComprasPorProveedor(String nombreProveedor, Pageable pageable) {
        log.info("Listando compras filtrando por proveedor nombre='{}'", nombreProveedor);

        Page<CompraResponseDTO> result = compraRepository
                .findByProveedorNombreContainingIgnoreCase(nombreProveedor, pageable)
                .map(this::mapToResponse);

        log.info("Se encontraron {} compras para proveedor nombre='{}'",
                result.getContent().size(), nombreProveedor);

        return result;
    }

    // --------------------------------------------------------
    // LISTAR TODAS
    // --------------------------------------------------------
    public Page<CompraResponseDTO> listarCompras(Pageable pageable) {
        log.info("Listando todas las compras con paginación page={} size={}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<CompraResponseDTO> result = compraRepository.findAll(pageable).map(this::mapToResponse);

        log.info("Total de compras obtenidas: {}", result.getContent().size());

        return result;
    }

    // --------------------------------------------------------
    // OBTENER POR ID
    // --------------------------------------------------------
    public Optional<CompraResponseDTO> obtenerPorId(Long id) {
        log.info("Buscando compra por id={}", id);

        Optional<CompraResponseDTO> result = compraRepository.findById(id)
                .map(this::mapToResponse);

        if (result.isPresent()) {
            log.info("Compra encontrada id={}", id);
        } else {
            log.warn("No se encontró compra con id={}", id);
        }

        return result;
    }

    // --------------------------------------------------------
    // ACTUALIZAR
    // --------------------------------------------------------
    public CompraResponseDTO actualizarCompra(Long id, CompraRequestDTO dto) {
        log.info("Actualizando compra id={} con proveedorId={} fecha={} total={}",
                id, dto.getProveedorId(), dto.getFecha(), dto.getTotal());

        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Compra no encontrada con id={}", id);
                    return new RuntimeException("Compra no encontrada");
                });

        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado con id={}", dto.getProveedorId());
                    return new RuntimeException("Proveedor no encontrado");
                });

        compra.setProveedor(proveedor);
        compra.setFecha(dto.getFecha());
        compra.setTotal(dto.getTotal());

        Compra updatedCompra = compraRepository.save(compra);

        log.info("Compra actualizada exitosamente id={}", updatedCompra.getId());

        return mapToResponse(updatedCompra);
    }

    // --------------------------------------------------------
    // BORRAR
    // --------------------------------------------------------
    public void borrarCompra(Long id) {
        log.info("Intentando eliminar compra con id={}", id);

        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Compra no encontrada al intentar eliminar id={}", id);
                    return new RuntimeException("Compra no encontrada");
                });

        compraRepository.delete(compra);

        log.info("Compra eliminada exitosamente id={}", id);
    }

    // --------------------------------------------------------
    // MAPEO A DTO
    // --------------------------------------------------------
    private CompraResponseDTO mapToResponse(Compra compra) {
        log.debug("Mapeando Compra a CompraResponseDTO id={}", compra.getId());

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
                    log.debug("Mapeando DetalleCompra id={} a DTO", detalle.getId());
                    DetalleCompraResponseDTO d = new DetalleCompraResponseDTO();
                    d.setId(detalle.getId());
                    d.setProductoId(detalle.getProducto().getId());
                    d.setCantidad(detalle.getCantidad());
                    d.setPrecio(detalle.getPrecio());
                    return d;
                })
                .collect(Collectors.toList());

        dto.setDetalles(detallesDTO);

        log.debug("Mapeo completado para Compra id={}", compra.getId());

        return dto;
    }
}
