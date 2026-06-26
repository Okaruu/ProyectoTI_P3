package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.EmpleadoDTO;
import com.example.ProyectoTI.model.Empleado;
import com.example.ProyectoTI.repository.EmpleadoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service    
@Transactional
@Slf4j
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<EmpleadoDTO> obtenerTodosLosEmpleados(){
        log.info("Obteniendo todos los empleados");
        return empleadoRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public EmpleadoDTO obtenerEmpleadoPorId(Integer idEmpleado){
        log.info("Obteniendo empleado con id: {}", idEmpleado);
        return empleadoRepository.findById(idEmpleado).map(this::convertirADTO).orElseThrow(() -> new RuntimeException("No se encontró el empleado con id: " + idEmpleado)); 
    }

    public Empleado guardarEmpleado (Empleado empleado){
        log.info("Guardando empleado: {}", empleado.getNombreEmpleado());
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizarEmpleado(Integer idEmpleado, Empleado empleado) {
        log.info("Actualizando empleado con id: {}", idEmpleado);
        Empleado emp = empleadoRepository.findById(idEmpleado).orElseThrow(() -> new RuntimeException("No se encontró el empleado con id: " + idEmpleado));
        if (empleado.getRutEmpleado() != null) {
            emp.setRutEmpleado(empleado.getRutEmpleado());
        }
        if (empleado.getNombreEmpleado() != null) {
            emp.setNombreEmpleado(empleado.getNombreEmpleado());
        }
        if(empleado.getPuestoEmpleado() != null){
            emp.setPuestoEmpleado(empleado.getPuestoEmpleado());
        }
        return empleadoRepository.save(emp);
    }

    private EmpleadoDTO convertirADTO(Empleado empleado){
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setIdEmpleado(empleado.getIdEmpleado());
        empleadoDTO.setPuestoEmpleado(empleado.getPuestoEmpleado());
        empleadoDTO.setRutEmpleado(empleado.getRutEmpleado());
        empleadoDTO.setNombreEmpleado(empleado.getNombreEmpleado());
        empleadoDTO.setPuestoEmpleado(empleado.getPuestoEmpleado());
        empleadoDTO.setFechaIngreso(empleado.getFechaIngreso());
        if(empleado.getSucursal() != null){
            empleadoDTO.setIdSucursal(empleado.getSucursal().getIdSucursal());
        }
        if(empleado.getTipoEmpleado() != null){
            empleadoDTO.setIdPuestoEmpleado(empleado.getTipoEmpleado().getIdTipoEmpleado());
        }
        return empleadoDTO;
    }

    public String eliminarEmpleado(Integer idEmpleado){
        log.info("Eliminando empleado con id: {}", idEmpleado);
        try{
            Empleado empleado = empleadoRepository.findById(idEmpleado).orElseThrow(() -> new RuntimeException("No se encontró el empleado con id: " + idEmpleado));
            empleadoRepository.delete(empleado);
            return "Empleado con id: " + idEmpleado + " eliminado exitosamente.";
        }catch(Exception e){
            throw new RuntimeException("No se encontró el empleado con id: " + idEmpleado);
        }
    }
}
