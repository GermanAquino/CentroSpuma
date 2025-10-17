package com.example.demo.microservices.service;

import com.example.demo.microservices.dto.*;
import com.example.demo.entities.*;
import com.example.demo.microservices.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final RolRepository roleRepository;

    public UsuarioResponseDTO crearUsuario (UsuarioRequestDTO dto) {
        Role rol = roleRepository.findById(dto.getRolId()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(rol);

        //Primero se guarda el usuario para que tenga id
        Usuario savedUsuario = usuarioRepository.save(usuario);

        //Si viene perfil en el body, creamos y asignamos el mismo id del usuario
        if (dto.getPerfil() != null) {
            Perfil perfil = new Perfil();
            perfil.setId(savedUsuario.getId());
            perfil.setUsuario(savedUsuario);
            perfil.setTelefono(dto.getPerfil().getTelefono());
            perfil.setDireccion(dto.getPerfil().getDireccion());
            perfil.setAvatarUrl(dto.getPerfil().getAvatarUrl());
            savedUsuario.setPerfil(perfil);

            // Se guarda nuevamente para persistir el perfil
            Usuario finalUsuario = usuarioRepository.save(savedUsuario);
        }

        return mapToResponse(savedUsuario);
    }

    //Listar usuarios con paginacion y busqueda por nombre
    public Page<UsuarioResponseDTO> listarUsuarios(String nombre, Pageable pageable) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre, pageable).map(this::mapToResponse);
    }

    //Obtener usuario por id
    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return usuarioRepository.findById(id).map(this::mapToResponse);
    }

    //Actualizar usuario y perfil, sino existe perfil se crea con el mismo id que el usuario
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(dto.getContrasena());

        Role rol = roleRepository.findById(dto.getRolId()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        if (dto.getPerfil() != null) {
            Perfil perfil = usuario.getPerfil();

            if (perfil == null) {
                perfil = new Perfil();
                perfil.setId(usuario.getId()); // Mismo ID que usuario
                perfil.setUsuario(usuario);
                usuario.setPerfil(perfil);
            }
            perfil.setTelefono(dto.getPerfil().getTelefono());
            perfil.setDireccion(dto.getPerfil().getDireccion());
            perfil.setAvatarUrl(dto.getPerfil().getAvatarUrl());
        }

        return mapToResponse(usuarioRepository.save(usuario));
    }

    public void borrarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // En el caso de querer implementar borrado logico
        // usuario.setActivo(false);
        usuarioRepository.delete(usuario);
    }

    //Mapeat entidad Usuario a DTO de respuesta
    private UsuarioResponseDTO mapToResponse(Usuario u) {
        PerfilResponseDTO perfilDTO = null;
        if (u.getPerfil() != null) {
            perfilDTO = new PerfilResponseDTO(
                u.getPerfil().getId(),
                u.getPerfil().getTelefono(),
                u.getPerfil().getDireccion(),
                u.getPerfil().getAvatarUrl()
            );
        }
        RolResponseDTO rolDTO = new RolResponseDTO(u.getRol().getId(), u.getRol().getNombre());
        return new UsuarioResponseDTO(u.getId(), u.getNombre(), u.getEmail(), rolDTO, perfilDTO);
    }
}
