package com.example.instrumento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.instrumento.DTO.InstrumentoDTO;
import com.example.instrumento.model.Instrumento;
import com.example.instrumento.repository.InstrumentoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    public List<Instrumento> obtenerTodos() {
        log.info("Obteniendo todos los instrumentos");
        return instrumentoRepository.findAll();
    }

    public Instrumento obtenerPorId(Integer idInstrumento) {
        log.info("Obteniendo instrumento con id: {}", idInstrumento);
        return instrumentoRepository.findById(idInstrumento).orElse(null);
    }

    public Instrumento guardarInstrumento(InstrumentoDTO instrumentoDTO) {
        log.info("Guardando instrumento: {}", instrumentoDTO.getNombreInstrumento());
        return instrumentoRepository.save(convertirAEntidad(instrumentoDTO));
    }

    public Instrumento actualizarInstrumento(Integer idInstrumento, InstrumentoDTO instrumentoDTO) {
        log.info("Actualizando instrumento con id: {}", idInstrumento);
        Instrumento instrumento = instrumentoRepository.findById(idInstrumento).orElseThrow(() -> new RuntimeException("No se encontro el instrumento con id: " + idInstrumento));

        if (instrumentoDTO.getNombreInstrumento() != null) instrumento.setNombreInstrumento(instrumentoDTO.getNombreInstrumento());
        if (instrumentoDTO.getDescripcionInstrumento() != null) instrumento.setDescripcionInstrumento(instrumentoDTO.getDescripcionInstrumento());
        if (instrumentoDTO.getStockInstrumento() != null) instrumento.setStockInstrumento(instrumentoDTO.getStockInstrumento());
        if (instrumentoDTO.getPrecioInstrumento() != null) instrumento.setPrecioInstrumento(instrumentoDTO.getPrecioInstrumento());
        if (instrumentoDTO.getNombreMarca() != null) instrumento.setNombreMarca(instrumentoDTO.getNombreMarca());
        if (instrumentoDTO.getNombreCategoria() != null) instrumento.setNombreCategoria(instrumentoDTO.getNombreCategoria());
        if (instrumentoDTO.getDetalleCategoria() != null) instrumento.setDetalleCategoria(instrumentoDTO.getDetalleCategoria());

        return instrumentoRepository.save(instrumento);
    }

    public String eliminarInstrumento(Integer idInstrumento) {
        log.info("Eliminando instrumento con id: {}", idInstrumento);
        try {
            Instrumento instrumento = instrumentoRepository.findById(idInstrumento).orElseThrow(() -> new RuntimeException("No se encontro el instrumento con id: " + idInstrumento));
            instrumentoRepository.delete(instrumento);
            return "Instrumento eliminado correctamente";
        } catch (Exception e) {
            return "Error al eliminar el instrumento: " + e.getMessage();
        }
    }

    private Instrumento convertirAEntidad(InstrumentoDTO dto) {
        Instrumento instrumento = new Instrumento();
        instrumento.setNombreInstrumento(dto.getNombreInstrumento());
        instrumento.setDescripcionInstrumento(dto.getDescripcionInstrumento());
        instrumento.setStockInstrumento(dto.getStockInstrumento());
        instrumento.setPrecioInstrumento(dto.getPrecioInstrumento());
        instrumento.setNombreMarca(dto.getNombreMarca());
        instrumento.setNombreCategoria(dto.getNombreCategoria());
        instrumento.setDetalleCategoria(dto.getDetalleCategoria());
        return instrumento;
    }

}