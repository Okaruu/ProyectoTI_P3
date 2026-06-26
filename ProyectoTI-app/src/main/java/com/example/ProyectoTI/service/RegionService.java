package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.RegionDTO;
import com.example.ProyectoTI.model.Region;
import com.example.ProyectoTI.repository.RegionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public List<RegionDTO> obtenerTodos(){
        log.info("Obteniendo todas las regiones");
        return regionRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public Region obtenerPorId(Integer idRegion){
        log.info("Obteniendo región con id: {}", idRegion);
        return regionRepository.findById(idRegion).orElse(null);
    }

    public Region guardarRegion(Region region){
        log.info("Guardando región: {}", region.getNombreRegion());
        return regionRepository.save(region);
    }
    private RegionDTO convertirADTO(Region region){
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setIdRegion(region.getIdRegion());
        regionDTO.setNombreRegion(region.getNombreRegion());
        return regionDTO;
    }


}
