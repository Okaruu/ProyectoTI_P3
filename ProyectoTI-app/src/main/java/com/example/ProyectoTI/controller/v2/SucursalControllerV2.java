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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoTI.DTO.SucursalDTO;
import com.example.ProyectoTI.assemblers.SucursalModelAssembler;
import com.example.ProyectoTI.model.Sucursal;
import com.example.ProyectoTI.service.SucursalService;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("sucursalControllerV2")
@RequestMapping("/api/v2/sucursales")
@Tag(name = "Sucursales", description = "Operaciones realacionadas con las sucursales.")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<SucursalDTO>>> obtenerTodasLasSucursales() {
        List<EntityModel<SucursalDTO>> sucursales = sucursalService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                sucursales,
                linkTo(methodOn(SucursalControllerV2.class).obtenerTodasLasSucursales()).withSelfRel()
        ));
        }

    @GetMapping(value = "/{idSucursal}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SucursalDTO>> obtenerSucursalPorId(@PathVariable Integer idSucursal) {
        try {
            SucursalDTO sucursal = sucursalService.obtenerPorId(idSucursal);
            return ResponseEntity.ok(assembler.toModel(sucursal));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{idSucursal}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SucursalDTO>> actualizarSucursal(@PathVariable Integer idSucursal, @RequestBody SucursalDTO sucursal) {
        try {
            Sucursal actualizada = sucursalService.actualizarSucursal(idSucursal, sucursal);
            SucursalDTO dto = sucursalService.obtenerPorId(actualizada.getIdSucursal());
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}