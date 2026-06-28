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

import net.datafaker.Faker;

@SpringBootTest
public class VentaServiceTest {

    @Autowired
    private VentaService ventaService;

    @MockitoBean
    private VentaRepository ventaRepository;

    private static final Faker faker = new Faker();

    private Venta createVenta(){
        return new Venta(1, null, null,
            Date.valueOf(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toLocalDateTime().toLocalDate()),
            faker.number().randomDouble(2, 5000, 200000),
            null
        );
    }

    @Test
    public void testObtenerTodos(){
        Venta venta = createVenta();
        when(ventaRepository.findAll()).thenReturn(List.of(venta));
        List<VentaDTO> ventas = ventaService.obtenerTodos();
        assertNotNull(ventas);
        assertEquals(1, ventas.size());
        assertEquals(venta.getPrecioFinal(), ventas.get(0).getPrecioFinal());
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
        Venta ventaEsperada = createVenta();
        when(ventaRepository.findById(1)).thenReturn(Optional.of(ventaEsperada));
        VentaDTO venta = ventaService.obtenerPorId(1);
        assertNotNull(venta);
        assertEquals(ventaEsperada.getPrecioFinal(), venta.getPrecioFinal());
    }

    @Test
    public void testGuardarVenta(){
        Venta venta = createVenta();
        when(ventaRepository.save(venta)).thenReturn(venta);
        Venta guardada = ventaService.guardarVenta(venta);
        assertNotNull(guardada);
        assertEquals(venta.getPrecioFinal(), guardada.getPrecioFinal());
    }

}