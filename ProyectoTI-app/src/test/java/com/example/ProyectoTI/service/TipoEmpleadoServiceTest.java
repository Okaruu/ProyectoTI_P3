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

@SpringBootTest
public class TipoEmpleadoServiceTest {

    @Autowired
    private TipoEmpleadoService tipoEmpleadoService;

    @MockitoBean
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    private TipoEmpleado createTipoEmpleado(){
        return new TipoEmpleado(1, "Vendedor", "Full Time", 600000.0);
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
        when(tipoEmpleadoRepository.findById(1)).thenReturn(Optional.of(createTipoEmpleado()));
        TipoEmpleado tipo = tipoEmpleadoService.obtenerPorId(1);
        assertNotNull(tipo);
        assertEquals("Vendedor", tipo.getPuesto());
    }

    @Test
    public void testGuardarTipoEmpleado(){
        TipoEmpleado tipo = createTipoEmpleado();
        when(tipoEmpleadoRepository.save(tipo)).thenReturn(tipo);
        TipoEmpleado guardado = tipoEmpleadoService.guardarTipoEmpleado(tipo);
        assertNotNull(guardado);
        assertEquals("Vendedor", guardado.getPuesto());
    }

    @Test
    public void testActualizarTipoEmpleado(){
        TipoEmpleado existente = createTipoEmpleado();
        TipoEmpleado patchData = new TipoEmpleado();
        patchData.setPuesto("Supervisor");

        when(tipoEmpleadoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(tipoEmpleadoRepository.save(existente)).thenReturn(existente);

        TipoEmpleado actualizado = tipoEmpleadoService.actualizarTipoEmpleado(1, patchData);
        assertNotNull(actualizado);
        assertEquals("Supervisor", actualizado.getPuesto());
    }

    @Test
    public void testEliminarTipoEmpleado(){
        TipoEmpleado tipo = createTipoEmpleado();
        when(tipoEmpleadoRepository.findById(1)).thenReturn(Optional.of(tipo));
        String resultado = tipoEmpleadoService.eliminarTipoEmpleado(1);
        assertEquals("Tipo de empleado con id: 1 eliminado correctamente", resultado);
    }

}