package com.example.ProyectoTI.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.ProyectoTI.DTO.ProductoDTO;
import com.example.ProyectoTI.controller.v1.ProductoController;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>>{

    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO producto){
        return EntityModel.of(producto, linkTo(methodOn(ProductoController.class).obtenerProductoPorId(producto.getIdProducto())).withSelfRel(),
        linkTo(methodOn(ProductoController.class).obtenerTodosLosInstrumentos()).withRel("productos"),
        linkTo(methodOn(ProductoController.class).listarPorCategoria(producto.getIdCategoria())).withRel("categoria"),
        linkTo(methodOn(ProductoController.class).listarPorMarca(producto.getIdMarca())).withRel("marca"));
    }

}
