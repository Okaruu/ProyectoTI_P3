package com.example.ProyectoTI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.ProyectoTI.DTO.SucursalDTO;
import com.example.ProyectoTI.model.Comuna;
import com.example.ProyectoTI.model.Region;
import com.example.ProyectoTI.model.Sucursal;
import com.example.ProyectoTI.repository.RegionRepository;
import com.example.ProyectoTI.repository.SucursalRepository;

@SpringBootTest
public class SucursalServiceTest {

    @Autowired
    private SucursalService sucursalService;

    @MockitoBean
    private SucursalRepository sucursalRepository;

    @MockitoBean
    private RegionRepository regionRepository;

    private Region createRegion(){
        return new Region(1, "Metropolitana");
    }

    private Comuna createComuna(){
        return new Comuna(1, "Providencia");
    }

    private Sucursal createSucursal(){
        return new Sucursal(1, "Av. Siempre Viva", "742", createComuna(), createRegion(), null);
    }

    @Test
    public void testObtenerTodos(){
        when(sucursalRepository.findAll()).thenReturn(List.of(createSucursal()));
        List<SucursalDTO> sucursales = sucursalService.obtenerTodos();
        assertNotNull(sucursales);
        assertEquals(1, sucursales.size());
        assertEquals("Av. Siempre Viva", sucursales.get(0).getCalle());
    }

    @Test
    public void testObtenerPorId(){
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(createSucursal()));
        SucursalDTO sucursal = sucursalService.obtenerPorId(1);
        assertNotNull(sucursal);
        assertEquals("742", sucursal.getNumeroCalle());
    }

    @Test
    public void testGuardarSucursal(){
        Sucursal sucursal = createSucursal();
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
        Sucursal savedSucursal = sucursalService.guardarSucursal(sucursal);
        assertNotNull(savedSucursal);
        assertEquals("Av. Siempre Viva", savedSucursal.getCalle());
    }

    @Test
    public void testActualizarSucursal(){
        Sucursal existente = createSucursal();
        SucursalDTO dto = new SucursalDTO();
        dto.setNumeroCalle("999");
        dto.setIdRegion(1);

        when(sucursalRepository.findById(1)).thenReturn(Optional.of(existente));
        when(regionRepository.findById(1)).thenReturn(Optional.of(createRegion()));
        when(sucursalRepository.save(existente)).thenReturn(existente);

        Sucursal actualizada = sucursalService.actualizarSucursal(1, dto);
        assertNotNull(actualizada);
        assertEquals("999", actualizada.getNumeroCalle());
    }

}