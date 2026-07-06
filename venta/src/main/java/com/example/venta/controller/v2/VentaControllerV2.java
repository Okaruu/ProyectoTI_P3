package com.example.venta.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.DTO.VentaDTO;
import com.example.venta.assembler.VentaModelAssembler;
import com.example.venta.model.Venta;
import com.example.venta.service.VentaService;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("ventaControllerV2")
@RequestMapping("/api/v2/ventas")
@Tag(name = "Ventas", description = "Operaciones relacionadas con las ventas.")
public class VentaControllerV2 {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> obtenerTodasLasVentas() {
        List<EntityModel<Venta>> ventas = ventaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                ventas,linkTo(methodOn(ventaControllerV2.class).obtenerTodasLasVentas()).withSelfRel()));
    }

    @GetMapping(value = "/{idVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Venta>> obtenerVentaPorId(@PathVariable Integer idVenta) {
        try {
            Venta venta = ventaService.obtenerPorId(idVenta);
            return ResponseEntity.ok(assembler.toModel(venta));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Venta>> agregarVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            Venta ventaGuardada = ventaService.guardarVenta(ventaDTO);
            return ResponseEntity.created(linkTo(methodOn(VentaControllerV2.class).obtenerVentaPorId(ventaGuardada.getIdVenta())).toUri()).body(assembler.toModel(ventaGuardada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{idVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Venta>> editarVenta(@PathVariable Integer idVenta, @RequestBody VentaDTO ventaDTO) {
        try {
            Venta ventaEditada = ventaService.actualizarVenta(idVenta, ventaDTO);
            return ResponseEntity.ok(assembler.toModel(ventaEditada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{idVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Venta>> actualizarVenta(@PathVariable Integer idVenta, @RequestBody VentaDTO ventaDTO) {
        try {
            Venta ventaActualizada = ventaService.actualizarVenta(idVenta, ventaDTO);
            return ResponseEntity.ok(assembler.toModel(ventaActualizada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{idVenta}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Integer idVenta) {
        try {
            ventaService.eliminarVenta(idVenta);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
