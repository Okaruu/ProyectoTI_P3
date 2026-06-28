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

import com.example.ProyectoTI.DTO.TipoEmpleadoDTO;
import com.example.ProyectoTI.model.TipoEmpleado;
import com.example.ProyectoTI.repository.TipoEmpleadoRepository;

import net.datafaker.Faker;

@SpringBootTest
public class TipoEmpleadoServiceTest {

    @Autowired
    private TipoEmpleadoService tipoEmpleadoService;

    @MockitoBean
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    private static final Faker faker = new Faker();

    private TipoEmpleado createTipoEmpleado(){
        return new TipoEmpleado(1,
            faker.job().title(),
            faker.options().option("Full Time", "Part Time", "Por Turnos"),
            faker.number().randomDouble(2, 400000, 1200000)
        );
    }

    @Test
    public void testObtenerTodos(){
        when(tipoEmpleadoRepository.findAll()).thenReturn(List.of(createTipoEmpleado()));
        List<TipoEmpleadoDTO> tipos = tipoEmpleadoService.obtenerTodos();
        assertNotNull(tipos);
        assertEquals(1, tipos.size());
    }

    @Test
    public void testObtenerPorId(){
        TipoEmpleado tipoEsperado = createTipoEmpleado();
        when(tipoEmpleadoRepository.findById(1)).thenReturn(Optional.of(tipoEsperado));
        TipoEmpleado tipo = tipoEmpleadoService.obtenerPorId(1);
        assertNotNull(tipo);
        assertEquals(tipoEsperado.getPuesto(), tipo.getPuesto());
    }

    @Test
    public void testGuardarTipoEmpleado(){
        TipoEmpleado tipo = createTipoEmpleado();
        when(tipoEmpleadoRepository.save(tipo)).thenReturn(tipo);
        TipoEmpleado guardado = tipoEmpleadoService.guardarTipoEmpleado(tipo);
        assertNotNull(guardado);
        assertEquals(tipo.getPuesto(), guardado.getPuesto());
    }

    @Test
    public void testActualizarTipoEmpleado(){
        TipoEmpleado existente = createTipoEmpleado();
        String nuevoPuesto = faker.job().title();

        TipoEmpleado patchData = new TipoEmpleado();
        patchData.setPuesto(nuevoPuesto);

        when(tipoEmpleadoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(tipoEmpleadoRepository.save(existente)).thenReturn(existente);

        TipoEmpleado actualizado = tipoEmpleadoService.actualizarTipoEmpleado(1, patchData);
        assertNotNull(actualizado);
        assertEquals(nuevoPuesto, actualizado.getPuesto());
    }

    @Test
    public void testEliminarTipoEmpleado(){
        TipoEmpleado tipo = createTipoEmpleado();
        when(tipoEmpleadoRepository.findById(1)).thenReturn(Optional.of(tipo));
        String resultado = tipoEmpleadoService.eliminarTipoEmpleado(1);
        assertEquals("Tipo de empleado con id: 1 eliminado correctamente", resultado);
    }

}