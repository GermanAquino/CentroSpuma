package com.example.apigerman.service;

import com.example.apigerman.dto.CategoriaResponseDTO;
import com.example.apigerman.dto.ProductoRequestDTO;
import com.example.apigerman.dto.ProductoResponseDTO;
import com.example.apigerman.repository.CategoriaRepository;
import com.example.apigerman.repository.ProductoRepository;
import com.example.demo.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    // Crear un producto con sus categorías
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        if (dto.getCategoriaIds() != null && !dto.getCategoriaIds().isEmpty()) {
            Set<Categoria> categorias = dto.getCategoriaIds().stream()
                    .map(id -> categoriaRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id)))
                    .collect(Collectors.toSet());
            producto.setCategorias(categorias);
        }

        Producto saved = productoRepository.save(producto);
        return mapToResponse(saved);
    }

    // Listar productos (búsqueda opcional por nombre)
    public Page<ProductoResponseDTO> listarProductos(String nombre, Pageable pageable) {
        Page<Producto> page;
        if (nombre != null && !nombre.isEmpty()) {
            page = productoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            page = productoRepository.findAll(pageable);
        }
        return page.map(this::mapToResponse);
    }

    // Obtener producto por ID
    public Optional<ProductoResponseDTO> obtenerPorId(Long id) {
        return productoRepository.findById(id).map(this::mapToResponse);
    }

    // Actualizar producto y sus categorías
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        if (dto.getCategoriaIds() != null) {
            Set<Categoria> categorias = dto.getCategoriaIds().stream()
                    .map(catId -> categoriaRepository.findById(catId)
                            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + catId)))
                    .collect(Collectors.toSet());
            producto.setCategorias(categorias);
        }

        return mapToResponse(productoRepository.save(producto));
    }

    // Borrar producto
    public void borrarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

    // Mapeo de entidad a DTO
    private ProductoResponseDTO mapToResponse(Producto producto) {
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