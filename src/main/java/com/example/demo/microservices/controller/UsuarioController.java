package com.example.demo.microservices.controller;

import com.example.demo.microservices.dto.*;
import com.example.demo.microservices.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PaginationConfigService paginationConfigService;

    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@RequestBody UsuarioRequestDTO dto) {
        log.info("[POST] Creando nuevo usuario con nombre: {}", dto.getNombre());
        try {
            UsuarioResponseDTO nuevo = usuarioService.crearUsuario(dto);
            log.info("Usuario creado correctamente con ID: {}", nuevo.getId());
            return ResponseEntity.status(201).body(nuevo);
        } catch (Exception e) {
            log.error("Error al crear usuario con nombre {}", dto.getNombre(), e);
            throw e;
        }
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtener(@PathVariable Long id) {
        log.info("[GET] Solicitando usuario con ID: {}", id);
        return usuarioService.obtenerPorId(id)
                .map(usuario -> {
                    log.info("Usuario encontrado: ID {} - Nombre: {}", usuario.getId(), usuario.getNombre());
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> {
                    log.warn("Usuario con ID {} no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Listar usuarios con búsqueda opcional por nombre
    @GetMapping
    public List<UsuarioResponseDTO> listar(@RequestParam(defaultValue = "") String nombre) {
        log.info("[GET] Listando usuarios con paginación por defecto (nombre='{}')", nombre);

        Page<UsuarioResponseDTO> page = usuarioService.listarUsuarios(
                nombre,
                paginationConfigService.defaultPageable());

        log.info("Total de usuarios obtenidos: {}", page.getContent().size());

        return page.getContent();
    }

    // Actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        log.info("[PUT] Actualizando usuario con ID: {}", id);
        try {
            UsuarioResponseDTO actualizado = usuarioService.actualizarUsuario(id, dto);
            log.info("Usuario con ID {} actualizado correctamente", id);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            log.error("Error al actualizar usuario con ID {}", id, e);
            throw e;
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        log.warn("[DELETE] Solicitando eliminación de usuario con ID: {}", id);
        try {
            usuarioService.borrarUsuario(id);
            log.info("Usuario con ID {} eliminado correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error al eliminar usuario con ID {}", id, e);
            throw e;
        }
    }
}
