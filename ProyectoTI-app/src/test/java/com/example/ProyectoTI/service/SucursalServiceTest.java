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

import net.datafaker.Faker;

@SpringBootTest
public class SucursalServiceTest {

    @Autowired
    private SucursalService sucursalService;

    @MockitoBean
    private SucursalRepository sucursalRepository;

    @MockitoBean
    private RegionRepository regionRepository;

    private static final Faker faker = new Faker();

    private Region createRegion(){
        return new Region(1, faker.address().state());
    }

    private Comuna createComuna(){
        return new Comuna(1, faker.address().city());
    }

    private Sucursal createSucursal(){
        return new Sucursal(1,
            faker.address().streetName(),
            faker.address().buildingNumber(),
            createComuna(),
            createRegion(),
            null
        );
    }

    @Test
    public void testObtenerTodos(){
        Sucursal sucursal = createSucursal();
        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));
        List<SucursalDTO> sucursales = sucursalService.obtenerTodos();
        assertNotNull(sucursales);
        assertEquals(1, sucursales.size());
        assertEquals(sucursal.getCalle(), sucursales.get(0).getCalle());
    }

    @Test
    public void testObtenerPorId(){
        Sucursal sucursalEsperada = createSucursal();
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursalEsperada));
        SucursalDTO sucursal = sucursalService.obtenerPorId(1);
        assertNotNull(sucursal);
        assertEquals(sucursalEsperada.getNumeroCalle(), sucursal.getNumeroCalle());
    }

    @Test
    public void testGuardarSucursal(){
        Sucursal sucursal = createSucursal();
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
        Sucursal savedSucursal = sucursalService.guardarSucursal(sucursal);
        assertNotNull(savedSucursal);
        assertEquals(sucursal.getCalle(), savedSucursal.getCalle());
    }

    @Test
    public void testActualizarSucursal(){
        Sucursal existente = createSucursal();
        String nuevoNumero = faker.address().buildingNumber();

        SucursalDTO dto = new SucursalDTO();
        dto.setNumeroCalle(nuevoNumero);
        dto.setIdRegion(1);

        when(sucursalRepository.findById(1)).thenReturn(Optional.of(existente));
        when(regionRepository.findById(1)).thenReturn(Optional.of(createRegion()));
        when(sucursalRepository.save(existente)).thenReturn(existente);

        Sucursal actualizada = sucursalService.actualizarSucursal(1, dto);
        assertNotNull(actualizada);
        assertEquals(nuevoNumero, actualizada.getNumeroCalle());
    }

}