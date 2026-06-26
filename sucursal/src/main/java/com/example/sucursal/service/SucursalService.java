package com.example.sucursal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sucursal.DTO.EmpleadoDTO;
import com.example.sucursal.DTO.SucursalDTO;
import com.example.sucursal.model.Empleado;
import com.example.sucursal.model.Sucursal;
import com.example.sucursal.repository.SucursalRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> obtenerTodos() {
        log.info("Obteniendo todas las sucursales");
        return sucursalRepository.findAll();
    }

    public Sucursal obtenerPorId(Integer idSucursal) {
        log.info("Obteniendo sucursal con id: {}", idSucursal);
        return sucursalRepository.findById(idSucursal).orElse(null);
    }

    public Sucursal guardarSucursal(SucursalDTO sucursalDTO) {
        log.info("Guardando sucursal: {} {}", sucursalDTO.getCalle(), sucursalDTO.getNumeroCalle());
        return sucursalRepository.save(convertirAEntidad(sucursalDTO));
    }

    public Sucursal actualizarSucursal(Integer idSucursal, SucursalDTO sucursalDTO) {
        log.info("Actualizando sucursal con id: {}", idSucursal);
        Sucursal sucursal = sucursalRepository.findById(idSucursal).orElseThrow(() -> new RuntimeException("No se encontro la sucursal con id: " + idSucursal));

        if (sucursalDTO.getCalle() != null) sucursal.setCalle(sucursalDTO.getCalle());
        if (sucursalDTO.getNumeroCalle() != null) sucursal.setNumeroCalle(sucursalDTO.getNumeroCalle());
        if (sucursalDTO.getNombreComuna() != null) sucursal.setNombreComuna(sucursalDTO.getNombreComuna());
        if (sucursalDTO.getNombreRegion() != null) sucursal.setNombreRegion(sucursalDTO.getNombreRegion());

        if (sucursalDTO.getEmpleados() != null) {
            if (sucursal.getEmpleados() == null) {
                sucursal.setEmpleados(new ArrayList<>());
            } else {
                sucursal.getEmpleados().clear();
            }
            sucursal.getEmpleados().addAll(convertirEmpleados(sucursalDTO.getEmpleados(), sucursal));
        }

        return sucursalRepository.save(sucursal);
    }

    public String eliminarSucursal(Integer idSucursal) {
        log.info("Eliminando sucursal con id: {}", idSucursal);
        try {
            Sucursal sucursal = sucursalRepository.findById(idSucursal)
                    .orElseThrow(() -> new RuntimeException("No se encontro la sucursal con id: " + idSucursal));
            sucursalRepository.delete(sucursal);
            return "Sucursal eliminada correctamente";
        } catch (Exception e) {
            return "Error al eliminar la sucursal: " + e.getMessage();
        }
    }
    
    public Sucursal agregarEmpleado(Integer idSucursal, EmpleadoDTO empleadoDTO) {
        log.info("Agregando empleado {} a la sucursal con id: {}", empleadoDTO.getNombreEmpleado(), idSucursal);
        Sucursal sucursal = sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new RuntimeException("No se encontro la sucursal con id: " + idSucursal));

        if (sucursal.getEmpleados() == null) {
            sucursal.setEmpleados(new ArrayList<>());
        }
        sucursal.getEmpleados().add(convertirEmpleado(empleadoDTO, sucursal));
        return sucursalRepository.save(sucursal);
    }

    private Empleado convertirEmpleado(EmpleadoDTO dto, Sucursal sucursal) {
        Empleado empleado = new Empleado();
        empleado.setNombreEmpleado(dto.getNombreEmpleado());
        empleado.setPuestoEmpleado(dto.getPuestoEmpleado());
        empleado.setRutEmpleado(dto.getRutEmpleado());
        empleado.setFechaIngreso(dto.getFechaIngreso());
        empleado.setTipoEmpleado(dto.getTipoEmpleado());
        empleado.setSalario(dto.getSalario());
        empleado.setSucursal(sucursal);
        return empleado;
    }

    private List<Empleado> convertirEmpleados(List<EmpleadoDTO> dtos, Sucursal sucursal) {
        List<Empleado> empleados = new ArrayList<>();
        for (EmpleadoDTO d : dtos) {
            empleados.add(convertirEmpleado(d, sucursal));
        }
        return empleados;
    }

    private Sucursal convertirAEntidad(SucursalDTO dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setCalle(dto.getCalle());
        sucursal.setNumeroCalle(dto.getNumeroCalle());
        sucursal.setNombreComuna(dto.getNombreComuna());
        sucursal.setNombreRegion(dto.getNombreRegion());
        sucursal.setEmpleados(dto.getEmpleados() != null ? convertirEmpleados(dto.getEmpleados(), sucursal) : new ArrayList<>());
        return sucursal;
    }

}