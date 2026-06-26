package com.example.ProyectoTI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.ProyectoTI.model.Categoria;
import com.example.ProyectoTI.repository.CategoriaRepository;

@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @MockitoBean
    private CategoriaRepository categoriaRepository;

    private Categoria createCategoria(){
        return new Categoria(1, "Viento", "Instrumentos de viento");
    } 

    @Test
    public void testFindAll(){
        when(categoriaRepository.findAll()).thenReturn(List.of(createCategoria()));
        List<Categoria> categorias = categoriaService.obtenerTodos();
        assertNotNull(categorias);
        assertEquals(1, categorias.size());
    }

    @Test
    public void testFindById(){
        when(categoriaRepository.findById(1)).thenReturn(java.util.Optional.of(createCategoria()));
        Categoria categoria = categoriaService.obtenerPorId(1);
        assertNotNull(categoria);
        assertEquals("Viento", categoria.getNombreCategoria());
    }

    @Test
    public void testSave(){
        Categoria categoria = createCategoria();
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria savedCategoria = categoriaService.guardarCategoria(categoria);
        assertNotNull(savedCategoria);
        assertEquals("Viento", savedCategoria.getNombreCategoria());
    }

    @Test
    public void testPatchCategoria(){
        Categoria existingCategoria = createCategoria();
        Categoria patchData = new Categoria();
        patchData.setNombreCategoria("Estuche Instrumentos");
        patchData.setDetalleCategoria("Estuche para distintos instrumentos, cajas, etc.");

        when(categoriaRepository.findById(1)).thenReturn(java.util.Optional.of(existingCategoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(existingCategoria);

        Categoria patchedCategoria = categoriaService.actualizarCategoria(1, patchData);
        assertNotNull(patchedCategoria);
        assertEquals("", patchedCategoria.getNombreCategoria());
    }

    @Test
    public void testDeleteById(){
        doNothing().when(categoriaRepository).deleteById(1);
        categoriaService.eliminarCategoria(1);
        verify(categoriaRepository, times(1)).deleteById(1);
    }

}
