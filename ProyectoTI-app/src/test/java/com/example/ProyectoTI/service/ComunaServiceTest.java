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

@SpringBootTest
public class ComunaServiceTest {

    @Autowired
    private ComunaService comunaService;

    @MockitoBean
    private ComunaRepository comunaRepository;

    private Comuna createComuna(){
        return new Comuna(1, "Providencia");
    }

    @Test
    public void testObtenerTodos(){
        when(comunaRepository.findAll()).thenReturn(List.of(createComuna()));
        List<ComunaDTO> comunas = comunaService.obtenerTodos();
        assertNotNull(comunas);
        assertEquals(1, comunas.size());
        assertEquals("Providencia", comunas.get(0).getNombreComuna());
    }

    @Test
    public void testObtenerPorId(){
        when(comunaRepository.findById(1)).thenReturn(Optional.of(createComuna()));
        ComunaDTO comuna = comunaService.obtenerPorId(1);
        assertNotNull(comuna);
        assertEquals("Providencia", comuna.getNombreComuna());
    }

    @Test
    public void testGuardarComuna(){
        Comuna comuna = createComuna();
        when(comunaRepository.save(comuna)).thenReturn(comuna);
        Comuna savedComuna = comunaService.guardarComuna(comuna);
        assertNotNull(savedComuna);
        assertEquals("Providencia", savedComuna.getNombreComuna());
    }

}