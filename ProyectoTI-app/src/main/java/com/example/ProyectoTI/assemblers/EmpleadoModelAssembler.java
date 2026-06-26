package com.example.ProyectoTI.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.example.ProyectoTI.DTO.EmpleadoDTO;
import com.example.ProyectoTI.controller.v1.EmpleadoController;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<EmpleadoDTO, EntityModel<EmpleadoDTO>>{

    @Override
    public EntityModel<EmpleadoDTO> toModel(EmpleadoDTO empleado){
        return EntityModel.of(empleado, linkTo(methodOn(EmpleadoController.class).obtenerEmpleadoPorId(empleado.getIdEmpleado())).withSelfRel(),
            linkTo(methodOn(EmpleadoController.class).obtenerTodosLosEmpleados()).withRel("empleados"),
            linkTo(methodOn(EmpleadoController.class).obtenerVentasPorEmpleado(empleado.getIdEmpleado())).withRel("ventas"));
    }
}
