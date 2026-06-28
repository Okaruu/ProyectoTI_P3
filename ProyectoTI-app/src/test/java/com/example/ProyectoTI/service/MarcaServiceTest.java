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

import com.example.ProyectoTI.DTO.MarcaDTO;
import com.example.ProyectoTI.model.Marca;
import com.example.ProyectoTI.repository.MarcaRepository;
import com.example.ProyectoTI.repository.ProductoRepository;

import net.datafaker.Faker;

@SpringBootTest
public class MarcaServiceTest {

    @Autowired
    private MarcaService marcaService;

    @MockitoBean
    private MarcaRepository marcaRepository;

    @MockitoBean
    private ProductoRepository productoRepository;

    private static final Faker faker = new Faker();

    private Marca createMarca(){
        return new Marca(1, faker.company().name());
    }

    @Test
    public void testObtenerTodos(){
        when(marcaRepository.findAll()).thenReturn(List.of(createMarca()));
        List<MarcaDTO> marcas = marcaService.obtenerTodos();
        assertNotNull(marcas);
        assertEquals(1, marcas.size());
    }

    @Test
    public void testObtenerPorId(){
        Marca marcaEsperada = createMarca();
        when(marcaRepository.findById(1)).thenReturn(Optional.of(marcaEsperada));
        MarcaDTO marca = marcaService.obtenerPorId(1);
        assertNotNull(marca);
        assertEquals(marcaEsperada.getNombreMarca(), marca.getNombreMarca());
    }

    @Test
    public void testGuardarMarca(){
        Marca marca = createMarca();
        when(marcaRepository.save(marca)).thenReturn(marca);
        Marca savedMarca = marcaService.guardarMarca(marca);
        assertNotNull(savedMarca);
        assertEquals(marca.getNombreMarca(), savedMarca.getNombreMarca());
    }

    @Test
    public void testActualizarMarca(){
        Marca existente = createMarca();
        String nuevoNombre = faker.company().name();

        Marca patchData = new Marca();
        patchData.setNombreMarca(nuevoNombre);

        when(marcaRepository.findById(1)).thenReturn(Optional.of(existente));
        when(marcaRepository.save(existente)).thenReturn(existente);

        Marca actualizada = marcaService.actualizarMarca(1, patchData);
        assertNotNull(actualizada);
        assertEquals(nuevoNombre, actualizada.getNombreMarca());
    }

    @Test
    public void testEliminarMarca_sinProductosAsociados(){
        Marca marca = createMarca();
        when(marcaRepository.findById(1)).thenReturn(Optional.of(marca));
        when(productoRepository.existsByMarca(marca)).thenReturn(false);

        String resultado = marcaService.eliminarMarca(1);
        assertEquals("Marca eliminada correctamente", resultado);
    }

    @Test
    public void testEliminarMarca_conProductosAsociados(){
        Marca marca = createMarca();
        when(marcaRepository.findById(1)).thenReturn(Optional.of(marca));
        when(productoRepository.existsByMarca(marca)).thenReturn(true);

        String resultado = marcaService.eliminarMarca(1);
        assertEquals("No se puede eliminar la marca porque tiene productos asociados", resultado);
    }

}