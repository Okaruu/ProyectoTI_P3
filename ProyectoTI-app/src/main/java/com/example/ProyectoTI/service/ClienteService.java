package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.ClienteDTO;
import com.example.ProyectoTI.model.Cliente;
import com.example.ProyectoTI.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos(){
        log.info("Obteniendo todos los clientes");
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Integer idCliente){
        log.info("Obteniendo cliente con id: {}", idCliente);
        return clienteRepository.findById(idCliente).orElse(null);
    }

    public Cliente guardarCliente (Cliente nuevCliente){
        log.info("Guardando cliente: {}", nuevCliente.getNombreCliente());
        Cliente cliente = new Cliente();
        cliente.setIdCliente(nuevCliente.getIdCliente());
        cliente.setRutCliente(nuevCliente.getRutCliente());
        cliente.setNombreCliente(nuevCliente.getNombreCliente());
        cliente.setTelefonoCliente(nuevCliente.getTelefonoCliente());
        cliente.setEmailCliente(nuevCliente.getEmailCliente());
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Integer idCliente, ClienteDTO clienteDTO) {
        log.info("Actualizando cliente con id: {}", idCliente);
        Cliente client = clienteRepository.findById(idCliente).orElseThrow(() -> new RuntimeException("No se encontró el cliente con id: " + idCliente));
        if (client.getNombreCliente() != null) {
            client.setNombreCliente(clienteDTO.getNombreCliente());
        }
        if (client.getTelefonoCliente() != null) {
            client.setTelefonoCliente(clienteDTO.getTelefonoCliente());
        }
        if (client.getEmailCliente() != null) {
            client.setEmailCliente(clienteDTO.getEmailCliente());
        }
        return clienteRepository.save(client);
    }

    private ClienteDTO convertirADTO(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setRutCliente(cliente.getRutCliente());
        clienteDTO.setNombreCliente(cliente.getNombreCliente());
        clienteDTO.setTelefonoCliente(cliente.getTelefonoCliente());
        clienteDTO.setEmailCliente(cliente.getEmailCliente());
        if (cliente.getIdCliente() != null) {
            clienteDTO.setIdCliente(cliente.getIdCliente());
        } else {
            clienteDTO.setIdCliente(null);
        }
        return clienteDTO;
    }

    public List<ClienteDTO> buscarPorRut(String rutCliente) {
        log.info("Buscando cliente por RUT: {}", rutCliente);
        return clienteRepository.buscarPorRut(rutCliente).stream().map(this::convertirADTO).toList();
    }

    public String eliminarCliente(Integer idCliente) {
        log.info("Eliminando cliente con id: {}", idCliente);
        try{
            Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new RuntimeException("No se encontró el cliente con id: " + idCliente));
            clienteRepository.delete(cliente);
            return "El Cliente con id " + cliente.getIdCliente() + "fue eliminado correctamente";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

}
