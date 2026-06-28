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

import com.example.ProyectoTI.DTO.ComunaDTO;
import com.example.ProyectoTI.model.Comuna;
import com.example.ProyectoTI.repository.ComunaRepository;

import net.datafaker.Faker;

@SpringBootTest
public class ComunaServiceTest {

    @Autowired
    private ComunaService comunaService;

    @MockitoBean
    private ComunaRepository comunaRepository;

    private static final Faker faker = new Faker();

    private Comuna createComuna(){
        return new Comuna(1, faker.address().city());
    }

    @Test
    public void testObtenerTodos(){
        Comuna comuna = createComuna();
        when(comunaRepository.findAll()).thenReturn(List.of(comuna));
        List<ComunaDTO> comunas = comunaService.obtenerTodos();
        assertNotNull(comunas);
        assertEquals(1, comunas.size());
        assertEquals(comuna.getNombreComuna(), comunas.get(0).getNombreComuna());
    }

    @Test
    public void testObtenerPorId(){
        Comuna comuna = createComuna();
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        ComunaDTO comunaDTO = comunaService.obtenerPorId(1);
        assertNotNull(comunaDTO);
        assertEquals(comuna.getNombreComuna(), comunaDTO.getNombreComuna());
    }

    @Test
    public void testGuardarComuna(){
        Comuna comuna = createComuna();
        when(comunaRepository.save(comuna)).thenReturn(comuna);
        Comuna savedComuna = comunaService.guardarComuna(comuna);
        assertNotNull(savedComuna);
        assertEquals(comuna.getNombreComuna(), savedComuna.getNombreComuna());
    }

}