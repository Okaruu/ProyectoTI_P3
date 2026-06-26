package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.ComunaDTO;
import com.example.ProyectoTI.model.Comuna;
import com.example.ProyectoTI.repository.ComunaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<ComunaDTO> obtenerTodos(){
        log.info("Obteniendo todas las comunas");
        return comunaRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public ComunaDTO obtenerPorId(Integer idComuna){
        log.info("Obteniendo comuna con id: {}", idComuna);
        Comuna comuna = comunaRepository.findById(idComuna).orElseThrow(() -> new RuntimeException("No se encontró la comuna con id: " + idComuna));
        return convertirADTO(comuna);
    }

    public Comuna guardarComuna (Comuna comuna){
        log.info("Guardando comuna: {}", comuna.getNombreComuna());
        return comunaRepository.save(comuna);
    }

    private ComunaDTO convertirADTO(Comuna comuna){
        ComunaDTO comunaDTO = new ComunaDTO();
        comunaDTO.setIdComuna(comuna.getIdComuna());
        comunaDTO.setNombreComuna(comuna.getNombreComuna());
        return comunaDTO;
    }
}
