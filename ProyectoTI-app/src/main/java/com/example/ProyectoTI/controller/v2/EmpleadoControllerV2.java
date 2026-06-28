package com.example.ProyectoTI.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoTI.DTO.EmpleadoDTO;
import com.example.ProyectoTI.DTO.VentaDTO;
import com.example.ProyectoTI.assemblers.EmpleadoModelAssembler;
import com.example.ProyectoTI.assemblers.VentaModelAssembler;
import com.example.ProyectoTI.model.Empleado;
import com.example.ProyectoTI.service.EmpleadoService;
import com.example.ProyectoTI.service.VentaService;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("empleadoControllerV2")
@RequestMapping("/api/v2/empleados")
@Tag(name = "Empleado", description = "operaciones relacionadas a empleados")
public class EmpleadoControllerV2 {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private EmpleadoModelAssembler assembler;
    @Autowired
    private VentaModelAssembler ventaAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoDTO>>> obtenerTodosLosEmpleados() {
        List<EntityModel<EmpleadoDTO>> empleados = empleadoService.obtenerTodosLosEmpleados().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                empleados,
                linkTo(methodOn(EmpleadoControllerV2.class).obtenerTodosLosEmpleados()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{idEmpleado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EmpleadoDTO>> obtenerEmpleadoPorId(@PathVariable Integer idEmpleado) {
        try {
            EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorId(idEmpleado);
            return ResponseEntity.ok(assembler.toModel(empleado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EmpleadoDTO>> agregarEmpleado(@RequestBody Empleado empleado) {
        try {
            // Asumiendo que guardarEmpleado puede devolver el DTO, o necesitas un método auxiliar
            EmpleadoDTO nuevoEmpleado = empleadoService.obtenerEmpleadoPorId(
                    empleadoService.guardarEmpleado(empleado).getIdEmpleado());
            return ResponseEntity
                    .created(linkTo(methodOn(EmpleadoControllerV2.class).obtenerEmpleadoPorId(nuevoEmpleado.getIdEmpleado())).toUri())
                    .body(assembler.toModel(nuevoEmpleado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/ventas/{idEmpleado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<VentaDTO>>> obtenerVentasPorEmpleado(@PathVariable Integer idEmpleado) {
        List<EntityModel<VentaDTO>> ventas = ventaService.obtenerVentasPorEmpleado(idEmpleado).stream()
                .map(ventaAssembler::toModel)
                .collect(Collectors.toList());
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                ventas,
                linkTo(methodOn(EmpleadoControllerV2.class).obtenerVentasPorEmpleado(idEmpleado)).withSelfRel()
        ));
    }

    @PatchMapping(value = "/{idEmpleado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EmpleadoDTO>> editarEmpleado(@PathVariable Integer idEmpleado, @RequestBody Empleado empleado) {
        try {
            Empleado actualizado = empleadoService.actualizarEmpleado(idEmpleado, empleado);
            EmpleadoDTO dto = empleadoService.obtenerEmpleadoPorId(actualizado.getIdEmpleado());
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{idEmpleado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EmpleadoDTO>> actualizarEmpleado(@PathVariable Integer idEmpleado, @RequestBody Empleado empleado) {
        try {
            Empleado actualizado = empleadoService.actualizarEmpleado(idEmpleado, empleado);
            EmpleadoDTO dto = empleadoService.obtenerEmpleadoPorId(actualizado.getIdEmpleado());
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}