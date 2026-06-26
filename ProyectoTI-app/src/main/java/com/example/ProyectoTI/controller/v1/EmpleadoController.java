package com.example.ProyectoTI.controller.v1;

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

import com.example.ProyectoTI.DTO.EmpleadoDTO;
import com.example.ProyectoTI.DTO.VentaDTO;
import com.example.ProyectoTI.model.Empleado;
import com.example.ProyectoTI.service.EmpleadoService;
import com.example.ProyectoTI.service.VentaService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/empleados")
@Tag (name = "Empleado", description = "operaciones relacionadas a empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> obtenerTodosLosEmpleados() {
        List<EmpleadoDTO> empleados = empleadoService.obtenerTodosLosEmpleados();
        if(empleados.isEmpty()){
            return new ResponseEntity<>(empleados, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(empleados, HttpStatus.OK);
    }

    @GetMapping("/{idEmpleado}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoPorId(@PathVariable Integer idEmpleado) {
        try {
            EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorId(idEmpleado);
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Empleado> agregarEmpleado(@RequestBody Empleado empleado){
        try {
            Empleado nuevoEmpleado = empleadoService.guardarEmpleado(empleado);
            return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ventas/{idEmpleado}")
    public ResponseEntity<?> obtenerVentasPorEmpleado(@PathVariable Integer idEmpleado) {
        try {
            List<VentaDTO> ventas = ventaService.obtenerVentasPorEmpleado(idEmpleado);
            if (ventas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ventas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener las ventas: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{idEmpleado}")
    public ResponseEntity<Empleado> editarEmpleado(@PathVariable Integer idEmpleado, @RequestBody Empleado Empleado){
        try {
            Empleado empleadoActualizado = empleadoService.actualizarEmpleado(idEmpleado, Empleado);
            return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idEmpleado}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer idEmpleado, @RequestBody Empleado Empleado){
        try {
            Empleado empleadoActualizado = empleadoService.actualizarEmpleado(idEmpleado, Empleado);
            return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idEmpleado}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable Integer idEmpleado) {
        String resultado = empleadoService.eliminarEmpleado(idEmpleado);
        if (resultado.equals("Empleado eliminado exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}