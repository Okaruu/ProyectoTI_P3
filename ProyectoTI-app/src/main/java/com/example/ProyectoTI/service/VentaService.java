package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.VentaDTO;
import com.example.ProyectoTI.model.Venta;
import com.example.ProyectoTI.repository.VentaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class VentaService {
    
    @Autowired
    private VentaRepository ventaRepository;

    public List<VentaDTO> obtenerTodos(){
        log.info("Obteniendo todas las ventas");
        return ventaRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public List<VentaDTO> obtenerVentasPorEmpleado(Integer idEmpleado) {
        log.info("Obteniendo ventas del empleado con id: {}", idEmpleado);
        return ventaRepository.findByEmpleado_IdEmpleado(idEmpleado).stream().map(this::convertirADTO).toList();
    }

    public VentaDTO obtenerPorId(Integer idVenta){
        log.info("Obteniendo venta con id: {}", idVenta);
        return ventaRepository.findById(idVenta).map(this::convertirADTO).orElseThrow(() -> new RuntimeException("No se encontró la venta con id: " + idVenta));
    }

    public VentaDTO convertirADTO(Venta venta){
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setIdVenta(venta.getIdVenta());
        ventaDTO.setFechaVenta(venta.getFechaVenta());
        ventaDTO.setPrecioFinal(venta.getPrecioFinal());
        return ventaDTO;
    }

    public Venta guardarVenta (Venta venta){
        log.info("Guardando venta con id: {}", venta.getIdVenta());
        return ventaRepository.save(venta);
    }

}
