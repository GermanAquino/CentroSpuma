package com.example.apigerman.service;

import com.example.apigerman.dto.ClienteRequestDTO;
import com.example.apigerman.dto.ClienteResponseDTO;
import com.example.apigerman.repository.ClienteRepository;
import com.example.demo.entities.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // Crear cliente
    public ClienteResponseDTO crearCliente(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setRuc(dto.getRuc());

        Cliente saved = clienteRepository.save(cliente);
        return mapToResponse(saved);
    }

    // Listar clientes con paginación y filtro por nombre (opcional)
    public Page<ClienteResponseDTO> listarClientes(String nombre, Pageable pageable) {
        if (nombre == null || nombre.isEmpty()) {
            return clienteRepository.findAll(pageable).map(this::mapToResponse);
        }
        return clienteRepository.findByNombreContainingIgnoreCase(nombre, pageable).map(this::mapToResponse);
    }

    // Obtener cliente por id
    public Optional<ClienteResponseDTO> obtenerPorId(Long id) {
        return clienteRepository.findById(id).map(this::mapToResponse);
    }

    // Actualizar cliente
    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(dto.getNombre());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setRuc(dto.getRuc());

        return mapToResponse(clienteRepository.save(cliente));
    }

    // Borrar cliente
    public void borrarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

    // Mapeo de entidad a DTO de respuesta
    private ClienteResponseDTO mapToResponse(Cliente c) {
        return new ClienteResponseDTO(
            c.getId(),
            c.getNombre(),
            c.getDireccion(),
            c.getTelefono(),
            c.getRuc()
        );
    }
}
