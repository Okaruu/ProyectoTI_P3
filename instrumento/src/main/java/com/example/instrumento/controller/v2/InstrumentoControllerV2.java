package com.example.instrumento.controller.v2;

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

import com.example.instrumento.DTO.InstrumentoDTO;
import com.example.instrumento.assemblers.InstrumentoModelAssembler;
import com.example.instrumento.model.Instrumento;
import com.example.instrumento.service.InstrumentoService;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("instrumentoControllerV2")
@RequestMapping("/api/v2/instrumentos")
@Tag(name = "Instrumentos", description = "Operaciones relacionadas con los instrumentos.")
public class InstrumentoControllerV2 {

    @Autowired
    private InstrumentoService instrumentoService;
    @Autowired
    private InstrumentoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Instrumento>>> obtenerTodosLosInstrumentos() {
        List<EntityModel<Instrumento>> instrumentos = instrumentoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        if (instrumentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                instrumentos,
                linkTo(methodOn(InstrumentoControllerV2.class).obtenerTodosLosInstrumentos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{idInstrumento}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Instrumento>> obtenerInstrumentoPorId(@PathVariable Integer idInstrumento) {
        try {
            Instrumento instrumento = instrumentoService.obtenerPorId(idInstrumento);
            return ResponseEntity.ok(assembler.toModel(instrumento));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Instrumento>> agregarInstrumento(@RequestBody InstrumentoDTO instrumentoDTO) {
        try {
            Instrumento instrumentoGuardado = instrumentoService.guardarInstrumento(instrumentoDTO);
            return ResponseEntity
                    .created(linkTo(methodOn(InstrumentoControllerV2.class).obtenerInstrumentoPorId(instrumentoGuardado.getIdInstrumento())).toUri())
                    .body(assembler.toModel(instrumentoGuardado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{idInstrumento}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Instrumento>> editarInstrumento(@PathVariable Integer idInstrumento, @RequestBody InstrumentoDTO instrumentoDTO) {
        try {
            Instrumento instrumentoEditado = instrumentoService.actualizarInstrumento(idInstrumento, instrumentoDTO);
            return ResponseEntity.ok(assembler.toModel(instrumentoEditado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{idInstrumento}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Instrumento>> actualizarInstrumento(@PathVariable Integer idInstrumento, @RequestBody InstrumentoDTO instrumentoDTO) {
        try {
            Instrumento instrumentoActualizado = instrumentoService.actualizarInstrumento(idInstrumento, instrumentoDTO);
            return ResponseEntity.ok(assembler.toModel(instrumentoActualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{idInstrumento}")
    public ResponseEntity<?> eliminarInstrumento(@PathVariable Integer idInstrumento) {
        try {
            instrumentoService.eliminarInstrumento(idInstrumento);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}