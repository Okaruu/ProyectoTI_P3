package com.example.instrumento.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.ProyectoTI.DTO.InstrumentoDTO;
import com.example.ProyectoTI.controller.v1.InstrumentoController;

@Component
public class InstrumentoModelAssembler implements RepresentationModelAssembler<InstrumentoDTO, EntityModel<InstrumentoDTO>>{
    @Override
    public EntityModel<InstrumentoDTO> toModel(InstrumentoDTO instrumento){
        return EntityModel.of(instrumento, linkTo(methodOn(InstrumentoController.class).obtenerInstrumentoPorid(instrumento.getIdInstrumento())).withSelfRel(),
        linkTo(methodOn(InstrumentoController.class).obtenerTodosLosInstrumentos()).withRel("instrumentos"));
    }
}
