package com.example.ProyectoTI.service;

import com.example.ProyectoTI.repository.RegionRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.SucursalDTO;
import com.example.ProyectoTI.model.Sucursal;
import com.example.ProyectoTI.repository.SucursalRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private RegionRepository regionRepository;
    
    public List<SucursalDTO> obtenerTodos(){
        log.info("Obteniendo todas las sucursales");
        return sucursalRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public SucursalDTO obtenerPorId(Integer idSucursal){
        log.info("Obteniendo sucursal con id: {}", idSucursal);
        return sucursalRepository.findById(idSucursal).map(this::convertirADTO).orElseThrow(() -> new RuntimeException("No se encontró la sucursal con id: " + idSucursal));
    }

    public Sucursal guardarSucursal (Sucursal sucursal){
        log.info("Guardando sucursal en la calle: {}", sucursal.getCalle());
        return sucursalRepository.save(sucursal);
    }

    public Sucursal actualizarSucursal(Integer idSucursal, SucursalDTO sucursal) {
        log.info("Actualizando sucursal con id: {}", idSucursal);
        Sucursal suc = sucursalRepository.findById(idSucursal).orElseThrow(() -> new RuntimeException("No se encontró la sucursal con id: " + idSucursal));
        if (sucursal.getNumeroCalle() != null) {
            suc.setNumeroCalle(sucursal.getNumeroCalle());
        }
        if (sucursal.getIdRegion() != null) {
            suc.setRegion(regionRepository.findById(sucursal.getIdRegion()).orElseThrow(() -> new RuntimeException("No se encontró la región con id: " + sucursal.getIdRegion())));
        }
        return sucursalRepository.save(suc);
    }

    private SucursalDTO convertirADTO(Sucursal sucursal){
        SucursalDTO sucursalDTO = new SucursalDTO();
        sucursalDTO.setIdSucursal(sucursal.getIdSucursal());
        sucursalDTO.setCalle(sucursal.getCalle());
        sucursalDTO.setNumeroCalle(sucursal.getNumeroCalle());
        if (sucursal.getRegion() != null) {
            sucursalDTO.setIdRegion(sucursal.getRegion().getIdRegion());
        }
        if(sucursal.getComuna() != null){
            sucursalDTO.setIdComuna(sucursal.getComuna().getIdComuna());
        }
        if (sucursal.getEmpleados() != null) {
            sucursalDTO.setEmpleados(sucursal.getEmpleados().stream().map(empleado -> empleado.getNombreEmpleado()).toList());
        }
        return sucursalDTO;
    }
    
}
