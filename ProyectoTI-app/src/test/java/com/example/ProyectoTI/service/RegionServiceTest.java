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

import com.example.ProyectoTI.DTO.RegionDTO;
import com.example.ProyectoTI.model.Region;
import com.example.ProyectoTI.repository.RegionRepository;

@SpringBootTest
public class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @MockitoBean
    private RegionRepository regionRepository;

    private Region createRegion(){
        return new Region(1, "Metropolitana");
    }

    @Test
    public void testObtenerTodos(){
        when(regionRepository.findAll()).thenReturn(List.of(createRegion()));
        List<RegionDTO> regiones = regionService.obtenerTodos();
        assertNotNull(regiones);
        assertEquals(1, regiones.size());
        assertEquals("Metropolitana", regiones.get(0).getNombreRegion());
    }

    @Test
    public void testObtenerPorId(){
        when(regionRepository.findById(1)).thenReturn(Optional.of(createRegion()));
        Region region = regionService.obtenerPorId(1);
        assertNotNull(region);
        assertEquals("Metropolitana", region.getNombreRegion());
    }

    @Test
    public void testGuardarRegion(){
        Region region = createRegion();
        when(regionRepository.save(region)).thenReturn(region);
        Region savedRegion = regionService.guardarRegion(region);
        assertNotNull(savedRegion);
        assertEquals("Metropolitana", savedRegion.getNombreRegion());
    }

}