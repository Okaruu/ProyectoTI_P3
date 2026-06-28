package com.example.venta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.sucursal.controller.VentaController;
import com.example.sucursal.model.Venta;

@Component
public class VentaModelAssembler implements RepresentationModelAssember <Venta, EntityModel<Venta>>{
    
    @Override
    public EntityModel<Venta> toModel(Venta venta){
        return EntityModel.of(venta, linkTo(methodOn(VentaController.class).obtenerVentaPorId(venta.getIdVenta))).withSelfRel(),
        linkTo(methodOn(VentaController.class).obtenerTodasLasVentas()).withRel("ventas"),
        linkTo(methodOn(VentaController.class).);
    }
}
