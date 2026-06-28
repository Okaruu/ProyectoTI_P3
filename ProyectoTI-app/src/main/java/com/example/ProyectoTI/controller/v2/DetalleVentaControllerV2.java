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

import com.example.ProyectoTI.assemblers.DetalleVentaModelAssembler;
import com.example.ProyectoTI.model.DetalleVenta;
import com.example.ProyectoTI.model.Venta;
import com.example.ProyectoTI.service.DetalleVentasService;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("detalleVentaControllerV2")
@RequestMapping("/api/v2/detalle-ventas")
@Tag(name = "Detalle ventas", description = "Operaciones relacionadas con el detalle de las ventas.")
public class DetalleVentaControllerV2 {

    @Autowired
    private DetalleVentasService detalleVentasService;
    @Autowired
    private DetalleVentaModelAssembler assembler;

    @GetMapping(value = "/venta/{idVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<DetalleVenta>>> obtenerDetalleVentaPorVenta(@PathVariable Integer idVenta) {
        List<EntityModel<DetalleVenta>> detalles = detalleVentasService.obtenerPorDetalleVenta(idVenta).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                detalles,
                linkTo(methodOn(DetalleVentaControllerV2.class).obtenerDetalleVentaPorVenta(idVenta)).withSelfRel()
        ));
    }

    @PostMapping("/procesar/{idVenta}")
    public ResponseEntity<Venta> procesarVenta(@PathVariable Integer idVenta, @RequestBody List<DetalleVenta> detalles) {
        try {
            Venta ventaProcesada = detalleVentasService.procesarVenta(idVenta, detalles);
            return ResponseEntity.status(201).body(ventaProcesada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}