package com.example.ProyectoTI.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoTI.DTO.ClienteDTO;
import com.example.ProyectoTI.assemblers.ClienteModelAssembler;
import com.example.ProyectoTI.model.Cliente;
import com.example.ProyectoTI.service.ClienteService;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("clienteControllerV2")
@RequestMapping("/api/v2/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes.")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> obtenerTodosLosClientes() {
        List<EntityModel<Cliente>> clientes = clienteService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                linkTo(methodOn(ClienteControllerV2.class).obtenerTodosLosClientes()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{idCliente}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> obtenerClientePorId(@PathVariable Integer idCliente) {
        try {
            Cliente cliente = clienteService.obtenerPorId(idCliente);
            return ResponseEntity.ok(assembler.toModel(cliente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> agregarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteGuardado = clienteService.guardarCliente(cliente);
            return ResponseEntity
                    .created(linkTo(methodOn(ClienteControllerV2.class).obtenerClientePorId(clienteGuardado.getIdCliente())).toUri())
                    .body(assembler.toModel(clienteGuardado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{idCliente}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> editarCliente(@PathVariable Integer idCliente, @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente clienteEditado = clienteService.actualizarCliente(idCliente, clienteDTO);
            return ResponseEntity.ok(assembler.toModel(clienteEditado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{idCliente}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> actualizarCliente(@PathVariable Integer idCliente, @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente clienteActualizado = clienteService.actualizarCliente(idCliente, clienteDTO);
            return ResponseEntity.ok(assembler.toModel(clienteActualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Integer idCliente) {
        try {
            clienteService.eliminarCliente(idCliente);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}