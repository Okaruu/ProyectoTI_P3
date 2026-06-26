package com.example.ProyectoTI.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.ProyectoTI.controller.v1.ClienteController;
import com.example.ProyectoTI.model.Cliente;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente){
        return EntityModel.of(cliente, linkTo(methodOn(ClienteController.class).obtenerClientePorId(cliente.getIdCliente())).withSelfRel(),
            linkTo(methodOn(ClienteController.class).obtenerTodosLosClientes()).withRel("clientes"));
    }

}
