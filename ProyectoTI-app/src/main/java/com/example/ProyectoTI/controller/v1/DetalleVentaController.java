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

import com.example.ProyectoTI.model.DetalleVenta;
import com.example.ProyectoTI.model.Venta;
import com.example.ProyectoTI.service.DetalleVentasService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/detalle-ventas")
@Tag(name = "Detalle ventas", description = "Operaciones relacionadas con el detalle de las ventas.")
public class DetalleVentaController {

    @Autowired
    private DetalleVentasService detalleVentasService;
    
    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<?> obtenerDetalleVentaPorVenta(@PathVariable Integer idVenta){
        try {
            List<DetalleVenta> detalles = detalleVentasService.obtenerPorDetalleVenta(idVenta);
            if(detalles.isEmpty()){
                return new ResponseEntity<>("No se encontraron detalles de venta para la venta con id: " + idVenta, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(detalles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los detalles de venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/procesar/{idVenta}")
    public ResponseEntity<?> procesarVenta(@PathVariable Integer idVenta, @RequestBody List<DetalleVenta> detalles){
        try{
            Venta ventaProcesada = detalleVentasService.procesarVenta(idVenta, detalles);
            return new ResponseEntity<>(ventaProcesada, HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity<>("Error al procesar la venta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Error al procesar la venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
