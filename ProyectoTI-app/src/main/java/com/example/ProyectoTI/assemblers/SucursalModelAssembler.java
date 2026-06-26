package com.example.ProyectoTI.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.ProyectoTI.DTO.SucursalDTO;
import com.example.ProyectoTI.controller.v1.SucursalController;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalDTO, EntityModel<SucursalDTO>>{

    @Override
    public EntityModel<SucursalDTO> toModel(SucursalDTO sucursal){
        return EntityModel.of(sucursal, linkTo(methodOn(SucursalController.class).obtenerSucursalPorId(sucursal.getIdSucursal())).withSelfRel(),
        linkTo(methodOn(SucursalController.class).obtenerTodasLasSucursales()).withRel("sucursales"));
    }
}
