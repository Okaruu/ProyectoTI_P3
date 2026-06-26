package com.example.ProyectoTI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.ProyectoTI.model.Cliente;
import com.example.ProyectoTI.repository.ClienteRepository;

import net.datafaker.Faker;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockitoBean
    private ClienteRepository clienteRepository;

    private static final Faker faker = new Faker();

    private Cliente createCliente(){
        return new Cliente(1, "21.234.876-k",
            faker.name().firstName(),
            faker.phoneNumber().phoneNumber(),
            faker.internet().emailAddress()
        );
    }

    @Test
    public void testFindAll(){
        when(clienteRepository.findAll()).thenReturn(List.of(createCliente()));
        List<Cliente> clientes = clienteService.obtenerTodos();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
    }

    @Test
    public void testFindById(){
        Cliente clienteEncontrado = createCliente();
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteEncontrado));
        Cliente cliente = clienteService.obtenerPorId(1);
        assertNotNull(cliente);
        assertEquals(clienteEncontrado.getNombreCliente(), cliente.getNombreCliente());
    }

    @Test
    public void testSave(){
        Cliente cliente = createCliente();
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente savedCliente = clienteService.guardarCliente(cliente);
        assertNotNull(savedCliente);
        assertEquals(cliente.getNombreCliente(), savedCliente.getNombreCliente());
    }

}
