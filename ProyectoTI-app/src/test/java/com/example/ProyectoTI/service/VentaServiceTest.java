package com.example.ProyectoTI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.ProyectoTI.DTO.VentaDTO;
import com.example.ProyectoTI.model.Venta;
import com.example.ProyectoTI.repository.VentaRepository;

@SpringBootTest
public class VentaServiceTest {

    @Autowired
    private VentaService ventaService;

    @MockitoBean
    private VentaRepository ventaRepository;

    private Venta createVenta(){
        return new Venta(1, null, null, Date.valueOf("2026-06-22"), 15000.0, null);
    }

    @Test
    public void testObtenerTodos(){
        when(ventaRepository.findAll()).thenReturn(List.of(createVenta()));
        List<VentaDTO> ventas = ventaService.obtenerTodos();
        assertNotNull(ventas);
        assertEquals(1, ventas.size());
        assertEquals(15000.0, ventas.get(0).getPrecioFinal());
    }

    @Test
    public void testObtenerVentasPorEmpleado(){
        when(ventaRepository.findByEmpleado_IdEmpleado(1)).thenReturn(List.of(createVenta()));
        List<VentaDTO> ventas = ventaService.obtenerVentasPorEmpleado(1);
        assertNotNull(ventas);
        assertEquals(1, ventas.size());
    }

    @Test
    public void testObtenerPorId(){
        when(ventaRepository.findById(1)).thenReturn(Optional.of(createVenta()));
        VentaDTO venta = ventaService.obtenerPorId(1);
        assertNotNull(venta);
        assertEquals(15000.0, venta.getPrecioFinal());
    }

    @Test
    public void testGuardarVenta(){
        Venta venta = createVenta();
        when(ventaRepository.save(venta)).thenReturn(venta);
        Venta guardada = ventaService.guardarVenta(venta);
        assertNotNull(guardada);
        assertEquals(15000.0, guardada.getPrecioFinal());
    }

}


