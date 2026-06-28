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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoTI.DTO.VentaDTO;
import com.example.ProyectoTI.assemblers.VentaModelAssembler;
import com.example.ProyectoTI.model.Venta;
import com.example.ProyectoTI.service.VentaService;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("ventaControllerV2")
@RequestMapping("/api/v2/ventas")
@Tag(name = "Venta", description = "operaciones relacionadas a venta")
public class VentaControllerV2 {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private VentaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<VentaDTO>>> obtenerTodasLasVentas() {
        List<EntityModel<VentaDTO>> ventas = ventaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                ventas,
                linkTo(methodOn(VentaControllerV2.class).obtenerTodasLasVentas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{idVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<VentaDTO>> obtenerVentaPorId(@PathVariable Integer idVenta) {
        try {
            VentaDTO venta = ventaService.obtenerPorId(idVenta);
            return ResponseEntity.ok(assembler.toModel(venta));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/empleado/{idEmpleado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<VentaDTO>>> obtenerVentasPorEmpleado(@PathVariable Integer idEmpleado) {
        List<EntityModel<VentaDTO>> ventas = ventaService.obtenerVentasPorEmpleado(idEmpleado).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                ventas,
                linkTo(methodOn(VentaControllerV2.class).obtenerVentasPorEmpleado(idEmpleado)).withSelfRel()
        ));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<VentaDTO>> crearVenta(@RequestBody Venta venta) {
        try {
            Venta guardada = ventaService.guardarVenta(venta);
            VentaDTO dto = ventaService.obtenerPorId(guardada.getIdVenta());
            return ResponseEntity
                    .created(linkTo(methodOn(VentaControllerV2.class).obtenerVentaPorId(dto.getIdVenta())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}