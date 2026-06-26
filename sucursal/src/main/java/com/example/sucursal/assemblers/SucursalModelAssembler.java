package com.example.sucursal.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.sucursal.controller.SucursalController;
import com.example.sucursal.model.Sucursal;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>>{
    
    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal){
        return EntityModel.of(sucursal, linkTo(methodOn(SucursalController.class).obtenerSucursalPorId(sucursal.getIdSucursal())).withSelfRel(),
        linkTo(methodOn(SucursalController.class).obtenerTodasLasSucursales()).withRel("sucursales"));
    }
}
