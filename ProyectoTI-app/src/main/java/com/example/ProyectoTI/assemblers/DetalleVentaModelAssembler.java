package com.example.ProyectoTI.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.ProyectoTI.controller.v1.DetalleVentaController;
import com.example.ProyectoTI.model.DetalleVenta;

@Component
public class DetalleVentaModelAssembler implements RepresentationModelAssembler<DetalleVenta, EntityModel<DetalleVenta>> {

    @Override
    public EntityModel<DetalleVenta> toModel(DetalleVenta detalleVenta){
        return EntityModel.of(detalleVenta, linkTo(methodOn(DetalleVentaController.class).obtenerDetalleVentaPorVenta(detalleVenta.getVenta().getIdVenta())).withSelfRel());
    }
}
