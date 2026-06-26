package com.example.sucursal.controller;

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

import com.example.sucursal.DTO.EmpleadoDTO;
import com.example.sucursal.DTO.SucursalDTO;
import com.example.sucursal.model.Sucursal;
import com.example.sucursal.service.SucursalService;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasSucursales() {
        List<Sucursal> sucursales = sucursalService.obtenerTodos();
        if (!sucursales.isEmpty()) {
            return new ResponseEntity<>(sucursales, HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontraron sucursales", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idSucursal}")
    public ResponseEntity<?> obtenerSucursalPorId(@PathVariable Integer idSucursal) {
        try {
            Sucursal sucursal = sucursalService.obtenerPorId(idSucursal);
            return new ResponseEntity<>(sucursal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener la sucursal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarSucursal(@RequestBody SucursalDTO sucursalDTO) {
        try {
            Sucursal sucursalGuardada = sucursalService.guardarSucursal(sucursalDTO);
            return new ResponseEntity<>(sucursalGuardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar la sucursal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idSucursal}")
    public ResponseEntity<?> editarSucursal(@PathVariable Integer idSucursal, @RequestBody SucursalDTO sucursalDTO) {
        try {
            Sucursal sucursalEditada = sucursalService.actualizarSucursal(idSucursal, sucursalDTO);
            return new ResponseEntity<>(sucursalEditada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la sucursal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{idSucursal}")
    public ResponseEntity<?> actualizarSucursal(@PathVariable Integer idSucursal, @RequestBody SucursalDTO sucursalDTO) {
        try {
            Sucursal sucursalActualizada = sucursalService.actualizarSucursal(idSucursal, sucursalDTO);
            return new ResponseEntity<>(sucursalActualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la sucursal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idSucursal}")
    public ResponseEntity<?> eliminarSucursal(@PathVariable Integer idSucursal) {
        try {
            sucursalService.eliminarSucursal(idSucursal);
            return new ResponseEntity<>("Sucursal eliminada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la sucursal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{idSucursal}/empleados")
    public ResponseEntity<?> agregarEmpleado(@PathVariable Integer idSucursal, @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            Sucursal sucursal = sucursalService.agregarEmpleado(idSucursal, empleadoDTO);
            return new ResponseEntity<>(sucursal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el empleado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}