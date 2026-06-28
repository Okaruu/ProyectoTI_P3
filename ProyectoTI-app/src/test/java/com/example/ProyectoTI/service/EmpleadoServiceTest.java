package com.example.ProyectoTI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.ProyectoTI.DTO.EmpleadoDTO;
import com.example.ProyectoTI.model.Empleado;
import com.example.ProyectoTI.repository.EmpleadoRepository;

import net.datafaker.Faker;

@SpringBootTest
public class EmpleadoServiceTest {

    @Autowired
    private EmpleadoService empleadoService;

    @MockitoBean
    private EmpleadoRepository empleadoRepository;

    private static final Faker faker = new Faker();

    private Empleado createEmpleado(){
        return new Empleado(1, null, null,
            faker.name().fullName(),
            faker.job().title(),
            "12.345.678-9",
            Date.valueOf(faker.date().birthday().toLocalDateTime().toLocalDate())
        );
    }

    @Test
    public void testObtenerTodosLosEmpleados(){
        when(empleadoRepository.findAll()).thenReturn(List.of(createEmpleado()));
        List<EmpleadoDTO> empleados = empleadoService.obtenerTodosLosEmpleados();
        assertNotNull(empleados);
        assertEquals(1, empleados.size());
    }

    @Test
    public void testObtenerEmpleadoPorId(){
        Empleado empleadoEsperado = createEmpleado();
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleadoEsperado));
        EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorId(1);
        assertNotNull(empleado);
        assertEquals(empleadoEsperado.getNombreEmpleado(), empleado.getNombreEmpleado());
    }

    @Test
    public void testGuardarEmpleado(){
        Empleado empleado = createEmpleado();
        when(empleadoRepository.save(empleado)).thenReturn(empleado);
        Empleado savedEmpleado = empleadoService.guardarEmpleado(empleado);
        assertNotNull(savedEmpleado);
        assertEquals(empleado.getNombreEmpleado(), savedEmpleado.getNombreEmpleado());
    }

    @Test
    public void testActualizarEmpleado(){
        Empleado existente = createEmpleado();
        String nuevoPuesto = faker.job().position();

        Empleado patchData = new Empleado();
        patchData.setPuestoEmpleado(nuevoPuesto);

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(empleadoRepository.save(existente)).thenReturn(existente);

        Empleado actualizado = empleadoService.actualizarEmpleado(1, patchData);
        assertNotNull(actualizado);
        assertEquals(nuevoPuesto, actualizado.getPuestoEmpleado());
    }

    @Test
    public void testEliminarEmpleado(){
        Empleado empleado = createEmpleado();
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        doNothing().when(empleadoRepository).delete(empleado);

        String resultado = empleadoService.eliminarEmpleado(1);
        assertEquals("Empleado con id: 1 eliminado exitosamente.", resultado);
        verify(empleadoRepository, times(1)).delete(empleado);
    }

}