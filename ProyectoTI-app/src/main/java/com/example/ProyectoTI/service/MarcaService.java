package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.MarcaDTO;
import com.example.ProyectoTI.model.Marca;
import com.example.ProyectoTI.repository.MarcaRepository;
import com.example.ProyectoTI.repository.ProductoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public List<MarcaDTO> obtenerTodos(){
        log.info("Obteniendo todas las marcas");
        return marcaRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public MarcaDTO obtenerPorId(Integer idMarca){
        log.info("Obteniendo marca con id: {}", idMarca);
        Marca marca = marcaRepository.findById(idMarca).orElseThrow(() -> new RuntimeException("No se encontró la marca con id: " + idMarca));
        return convertirADTO(marca);
    }

    public Marca guardarMarca (Marca marca){
        log.info("Guardando marca: {}", marca.getNombreMarca());
        return marcaRepository.save(marca);
    }

    private MarcaDTO convertirADTO(Marca marca){
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setIdMarca(marca.getIdMarca());
        marcaDTO.setNombreMarca(marca.getNombreMarca());
        return marcaDTO;
    }

    public Marca actualizarMarca(Integer idMarca, Marca marca) {
        log.info("Actualizando marca con id: {}", idMarca);
        Marca marc = marcaRepository.findById(idMarca).orElseThrow(() -> new RuntimeException("No se encontró la marca con id: " + idMarca));
        if (marca.getNombreMarca() != null) {
            marc.setNombreMarca(marca.getNombreMarca());
        }
        return marcaRepository.save(marc);
    }

    public String eliminarMarca(Integer idMarca) {
        log.info("Eliminando marca con id: {}", idMarca);
        try{
            Marca marca = marcaRepository.findById(idMarca).orElseThrow(() -> new RuntimeException("No se encontró la marca con id: " + idMarca));
            if(productoRepository.existsByMarca(marca)){
                return "No se puede eliminar la marca porque tiene productos asociados";
            }
            marcaRepository.delete(marca);
            return "Marca eliminada correctamente";
        }catch(Exception e){
            return "Error al eliminar la marca: " + e.getMessage();
        }
    }
}
