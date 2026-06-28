package com.example.instrumento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.instrumento.model.Instrumento;
import com.example.instrumento.service.InstrumentoService;

@RestController
@RequestMapping("/api/v1/instrumentos")
public class InstrumentoController {

    @Autowired
    private InstrumentoService instrumentoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosInstrumentos() {
        List<Instrumento> instrumentos = instrumentoService.obtenerTodos();
        if (!instrumentos.isEmpty()) {
            return new ResponseEntity<>(instrumentos, HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontraron instrumentos", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idInstrumento}")
    public ResponseEntity<?> obtenerInstrumentoPorId(@PathVariable Integer idInstrumento) {
        try {
            Instrumento instrumento = instrumentoService.obtenerPorId(idInstrumento);
            return new ResponseEntity<>(instrumento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener el instrumento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarInstrumento(@RequestBody InstrumentoDTO instrumentoDTO) {
        try {
            Instrumento instrumentoGuardado = instrumentoService.guardarInstrumento(instrumentoDTO);
            return new ResponseEntity<>(instrumentoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el instrumento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idInstrumento}")
    public ResponseEntity<?> editarInstrumento(@PathVariable Integer idInstrumento, @RequestBody InstrumentoDTO instrumentoDTO) {
        try {
            Instrumento instrumentoEditado = instrumentoService.actualizarInstrumento(idInstrumento, instrumentoDTO);
            return new ResponseEntity<>(instrumentoEditado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el instrumento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{idInstrumento}")
    public ResponseEntity<?> actualizarInstrumento(@PathVariable Integer idInstrumento, @RequestBody InstrumentoDTO instrumentoDTO) {
        try {
            Instrumento instrumentoActualizado = instrumentoService.actualizarInstrumento(idInstrumento, instrumentoDTO);
            return new ResponseEntity<>(instrumentoActualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el instrumento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idInstrumento}")
    public ResponseEntity<?> eliminarInstrumento(@PathVariable Integer idInstrumento) {
        try {
            instrumentoService.eliminarInstrumento(idInstrumento);
            return new ResponseEntity<>("Instrumento eliminado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el instrumento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}