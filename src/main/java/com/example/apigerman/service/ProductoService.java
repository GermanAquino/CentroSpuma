package com.example.apigerman.service;

import com.example.apigerman.dto.CategoriaResponseDTO;
import com.example.apigerman.dto.ProductoRequestDTO;
import com.example.apigerman.dto.ProductoResponseDTO;
import com.example.apigerman.repository.CategoriaRepository;
import com.example.apigerman.repository.ProductoRepository;
import com.example.demo.entities.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    // Crear un producto con sus categorías
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        log.info("Creando un nuevo producto: nombre='{}'", dto.getNombre());

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        if (dto.getCategoriaIds() != null && !dto.getCategoriaIds().isEmpty()) {
            log.info("Asignando {} categorías al nuevo producto", dto.getCategoriaIds().size());

            Set<Categoria> categorias = dto.getCategoriaIds().stream()
                    .map(id -> categoriaRepository.findById(id).orElseThrow(() -> {
                        log.warn("Categoría no encontrada con ID: {}", id);
                        return new RuntimeException("Categoría no encontrada con ID: " + id);
                    }))
                    .collect(Collectors.toSet());

            producto.setCategorias(categorias);
        } else {
            log.info("El producto no tiene categorías asignadas");
        }

        Producto saved = productoRepository.save(producto);
        log.info("Producto creado con ID={}", saved.getId());

        return mapToResponse(saved);
    }

    // Listar productos (búsqueda opcional por nombre)
    public Page<ProductoResponseDTO> listarProductos(String nombre, Pageable pageable) {
        log.info("Listando productos. Filtro nombre='{}', page={}, size={}",
                nombre, pageable.getPageNumber(), pageable.getPageSize());

        Page<Producto> page;

        if (nombre != null && !nombre.isEmpty()) {
            log.info("Aplicando filtro por nombre...");
            page = productoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            log.info("Listando todos los productos (sin filtro)...");
            page = productoRepository.findAll(pageable);
        }

        log.info("Se encontraron {} productos", page.getContent().size());
        return page.map(this::mapToResponse);
    }

    // Obtener producto por ID
    public Optional<ProductoResponseDTO> obtenerPorId(Long id) {
        log.info("Buscando producto con ID={}", id);

        Optional<Producto> opt = productoRepository.findById(id);

        if (opt.isEmpty()) {
            log.warn("Producto con ID={} no encontrado", id);
        } else {
            log.info("Producto encontrado: {}", opt.get().getNombre());
        }

        return opt.map(this::mapToResponse);
    }

    // Actualizar producto y sus categorías
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        log.info("Actualizando producto con ID={}", id);

        Producto producto = productoRepository.findById(id).orElseThrow(() -> {
            log.warn("Producto no encontrado con ID={}", id);
            return new RuntimeException("Producto no encontrado");
        });

        log.info("Actualizando datos principales del producto...");
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        if (dto.getCategoriaIds() != null) {
            log.info("Actualizando categorías: {} categorías recibidas", dto.getCategoriaIds().size());

            Set<Categoria> categorias = dto.getCategoriaIds().stream()
                    .map(catId -> categoriaRepository.findById(catId).orElseThrow(() -> {
                        log.warn("Categoría no encontrada con ID={}", catId);
                        return new RuntimeException("Categoría no encontrada con ID: " + catId);
                    }))
                    .collect(Collectors.toSet());

            producto.setCategorias(categorias);
        } else {
            log.info("El request no incluyó categorías — se mantiene el valor anterior");
        }

        Producto updated = productoRepository.save(producto);
        log.info("Producto actualizado con ID={}", updated.getId());

        return mapToResponse(updated);
    }

    // Borrar producto
    public void borrarProducto(Long id) {
        log.info("Intentando borrar producto con ID={}", id);

        Producto producto = productoRepository.findById(id).orElseThrow(() -> {
            log.warn("Producto no encontrado con ID={}", id);
            return new RuntimeException("Producto no encontrado");
        });

        productoRepository.delete(producto);
        log.info("Producto con ID={} eliminado correctamente", id);
    }

    // Mapeo de entidad a DTO
    private ProductoResponseDTO mapToResponse(Producto producto) {
        log.debug("Mapeando entidad Producto a DTO. ID={}", producto.getId());
        Set<CategoriaResponseDTO> categoriasDTO = producto.getCategorias().stream()
                .map(cat -> new CategoriaResponseDTO(cat.getId(), cat.getNombre(), cat.getDescripcion()))
                .collect(Collectors.toSet());

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                categoriasDTO
        );
    }
}