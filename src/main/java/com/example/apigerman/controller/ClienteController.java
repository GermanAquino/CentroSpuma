package com.example.apigerman.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.apigerman.config.PaginationConfigService;
import com.example.apigerman.dto.ClienteRequestDTO;
import com.example.apigerman.dto.ClienteResponseDTO;
import com.example.apigerman.service.ClienteService;

@Slf4j
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final PaginationConfigService paginationConfigService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@RequestBody ClienteRequestDTO dto) {
        log.info("[POST] Creando nuevo cliente con nombre: {}", dto.getNombre());
        try {
            ClienteResponseDTO nuevoCliente = clienteService.crearCliente(dto);
            log.info("Cliente creado correctamente con ID: {}", nuevoCliente.getId());
            return ResponseEntity.status(201).body(nuevoCliente);
        } catch (Exception e) {
            log.error("Error al crear cliente con nombre: {}", dto.getNombre(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtener(@PathVariable Long id) {
        log.info("[GET] Solicitando cliente con ID: {}", id);
        return clienteService.obtenerPorId(id)
                .map(cliente -> {
                    log.info("Cliente encontrado: {}", cliente.getNombre());
                    return ResponseEntity.ok(cliente);
                })
                .orElseGet(() -> {
                    log.warn("Cliente con ID {} no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Listar todos los clientes
    @GetMapping
    public List<ClienteResponseDTO> listarClientes() {
        log.info("Listando clientes con paginación por defecto");

        Page<ClienteResponseDTO> page = clienteService.listarClientes(
                null, // sin filtro
                paginationConfigService.defaultPageable());

        log.info("Se obtuvieron {} clientes", page.getContent().size());

        return page.getContent();
    }

    // Listar clientes filtrando por nombre (PathVariable)
    @GetMapping("/nombre/{nombre}")
    public List<ClienteResponseDTO> listarPorNombre(@PathVariable String nombre) {
        log.info("Buscando clientes filtrados por nombre: '{}'", nombre);

        Page<ClienteResponseDTO> page = clienteService.listarClientes(
                nombre,
                paginationConfigService.defaultPageable());

        log.info("Se encontraron {} clientes con nombre '{}'",
                page.getContent().size(), nombre);

        return page.getContent();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        log.info("[PUT] Actualizando cliente con ID: {} - Nuevo nombre: {}", id, dto.getNombre());
        try {
            ClienteResponseDTO actualizado = clienteService.actualizarCliente(id, dto);
            log.info("Cliente actualizado correctamente con ID: {}", id);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            log.error("Error al actualizar cliente con ID {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        log.warn("[DELETE] Solicitando eliminación del cliente con ID: {}", id);
        try {
            clienteService.borrarCliente(id);
            log.info("Cliente con ID {} eliminado correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error al eliminar cliente con ID {}", id, e);
            throw e;
        }
    }
}