package com.example.ProyectoTI.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoTI.DTO.SucursalDTO;
import com.example.ProyectoTI.model.Sucursal;
import com.example.ProyectoTI.service.SucursalService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones realacionadas con las sucursales.")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> obtenerTodasLasSucursales(){
        List<SucursalDTO> sucursales = sucursalService.obtenerTodos();
        if(!sucursales.isEmpty()){
            return new ResponseEntity<>(sucursales, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idSucursal}")
    public ResponseEntity<SucursalDTO> obtenerSucursalPorId(@PathVariable Integer idSucursal){
        try {
            SucursalDTO sucursal = sucursalService.obtenerPorId(idSucursal);
            return new ResponseEntity<>(sucursal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{idSucursal}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable Integer idSucursal, @RequestBody SucursalDTO sucursal){
        try {
            Sucursal sucursalActualizada = sucursalService.actualizarSucursal(idSucursal, sucursal);
            return new ResponseEntity<>(sucursalActualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}