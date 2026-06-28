package com.example.venta.controller.v1;

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

import com.example.venta.DTO.VentaDTO;
import com.example.venta.model.Venta;
import com.example.venta.service.VentaService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Operaciones relacionadas con las ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaService.obtenerTodos();
        if (!ventas.isEmpty()) {
            return new ResponseEntity<>(ventas, HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontraron ventas", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idVenta}")
    public ResponseEntity<?> obtenerVentaPorId(@PathVariable Integer idVenta) {
        try {
            Venta venta = ventaService.obtenerPorId(idVenta);
            return new ResponseEntity<>(venta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener la venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            Venta ventaGuardada = ventaService.guardarVenta(ventaDTO);
            return new ResponseEntity<>(ventaGuardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar la venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idVenta}")
    public ResponseEntity<?> editarVenta(@PathVariable Integer idVenta, @RequestBody VentaDTO ventaDTO) {
        try {
            Venta ventaEditada = ventaService.actualizarVenta(idVenta, ventaDTO);
            return new ResponseEntity<>(ventaEditada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{idVenta}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Integer idVenta, @RequestBody VentaDTO ventaDTO) {
        try {
            Venta ventaActualizada = ventaService.actualizarVenta(idVenta, ventaDTO);
            return new ResponseEntity<>(ventaActualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idVenta}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Integer idVenta) {
        try {
            ventaService.eliminarVenta(idVenta);
            return new ResponseEntity<>("Venta eliminada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}