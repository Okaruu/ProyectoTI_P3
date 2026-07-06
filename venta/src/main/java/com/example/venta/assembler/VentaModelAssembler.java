package com.example.venta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.venta.controller.v1.VentaController;
import com.example.venta.model.Venta;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler <Venta, EntityModel<Venta>>{
    
    @Override
    public EntityModel<Venta> toModel(Venta venta){
        return EntityModel.of(venta,
            linkTo(methodOn(VentaController.class).obtenerVentaPorId(venta.getIdVenta())).withSelfRel(),
            linkTo(methodOn(VentaController.class).obtenerTodasLasVentas()).withRel("ventas"));
    }
}
