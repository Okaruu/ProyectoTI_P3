package com.example.ProyectoTI.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoTI.DTO.VentaDTO;
import com.example.ProyectoTI.model.Venta;
import com.example.ProyectoTI.service.VentaService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/ventas")
@Tag (name = "Venta", description = "operaciones relacionadas a venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasVentas(){
        List<VentaDTO> ventas = ventaService.obtenerTodos();
        if(ventas.isEmpty()){
            return new ResponseEntity<>("No se encontraron ventas", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @GetMapping("/{idVenta}")
    public ResponseEntity<?> obtenerVentaPorId(@PathVariable Integer idVenta){
        try {
            VentaDTO venta = ventaService.obtenerPorId(idVenta);
            return new ResponseEntity<>(venta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener la venta: " +  e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<?> ObtenerVentasPorEmpleado(@PathVariable Integer idEmpleado) {
        try {
            List<VentaDTO> ventas = ventaService.obtenerVentasPorEmpleado(idEmpleado);
            if(ventas.isEmpty()){
                return new ResponseEntity<>("No se encontraron ventas para el empleado con id: " + idEmpleado, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ventas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener las ventas: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody Venta venta){
        try {
            Venta ventaGuardada = ventaService.guardarVenta(venta);
            return new ResponseEntity<>(ventaGuardada, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Error al crear la venta:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
