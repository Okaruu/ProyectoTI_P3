package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.TipoEmpleadoDTO;
import com.example.ProyectoTI.model.TipoEmpleado;
import com.example.ProyectoTI.repository.TipoEmpleadoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TipoEmpleadoService {

    @Autowired
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    public List<TipoEmpleadoDTO> obtenerTodos(){
        log.info("Obteniendo todos los tipos de empleados");
        return tipoEmpleadoRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public TipoEmpleado obtenerPorId(Integer idTipoEmpleado){
        log.info("Obteniendo tipo de empleado con id: {}", idTipoEmpleado);
        return tipoEmpleadoRepository.findById(idTipoEmpleado).orElse(null);
    }

    private TipoEmpleadoDTO convertirADTO(TipoEmpleado tipoEmpleado){
        TipoEmpleadoDTO tipoEmpleadoDTO = new TipoEmpleadoDTO();
        tipoEmpleadoDTO.setIdTipoEmpleado(tipoEmpleado.getIdTipoEmpleado());
        tipoEmpleadoDTO.setTipoEmpleado(tipoEmpleado.getTipoEmpleado());
        tipoEmpleadoDTO.setPuestoEmpleado(tipoEmpleado.getPuesto());
        return tipoEmpleadoDTO;
    }

    public TipoEmpleado guardarTipoEmpleado (TipoEmpleado tipoEmpleado){
        log.info("Guardando tipo de empleado: {}", tipoEmpleado.getTipoEmpleado());
        return tipoEmpleadoRepository.save(tipoEmpleado);
    }

    // en caso de ascenso o cambio de puesto
    public TipoEmpleado actualizarTipoEmpleado(Integer idTipoEmpleado, TipoEmpleado tipoEmpleado) {
        log.info("Actualizando tipo de empleado con id: {}", idTipoEmpleado);
        TipoEmpleado tipoEmp = tipoEmpleadoRepository.findById(idTipoEmpleado).orElseThrow(() -> new RuntimeException("No se encontró el tipo de empleado con id: " + idTipoEmpleado));
        if (tipoEmpleado.getTipoEmpleado() != null) {
            tipoEmp.setTipoEmpleado(tipoEmpleado.getTipoEmpleado());
        }
        if (tipoEmpleado.getPuesto() != null) {
            tipoEmp.setPuesto(tipoEmpleado.getPuesto());
        }
        return tipoEmpleadoRepository.save(tipoEmp);
    }

    // por si se despide a alguien (ojalá que no pase)
    public String eliminarTipoEmpleado(Integer idTipoEmpleado) {
        log.info("Eliminando tipo de empleado con id: {}", idTipoEmpleado);
        try{
            TipoEmpleado tipoEmpleado = tipoEmpleadoRepository.findById(idTipoEmpleado).orElseThrow(() -> new RuntimeException("No se encontró el tipo de empleado con id: " + idTipoEmpleado));
            tipoEmpleadoRepository.delete(tipoEmpleado);
            return "Tipo de empleado con id: " + idTipoEmpleado + " eliminado correctamente";
        }catch(Exception e){
            return "Error al eliminar el tipo de empleado: " + e.getMessage();
        }
    }

}
