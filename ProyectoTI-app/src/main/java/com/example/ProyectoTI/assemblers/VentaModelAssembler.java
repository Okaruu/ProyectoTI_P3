package com.example.ProyectoTI.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.ProyectoTI.DTO.VentaDTO;
import com.example.ProyectoTI.controller.v1.VentaController;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<VentaDTO, EntityModel<VentaDTO>>{

    @Override
    public EntityModel<VentaDTO> toModel(VentaDTO venta){
        return EntityModel.of(venta, linkTo(methodOn(VentaController.class).obtenerVentaPorId(venta.getIdVenta())).withSelfRel(),
        linkTo(methodOn(VentaController.class).obtenerTodasLasVentas()).withRel("ventas"));
    }
}
