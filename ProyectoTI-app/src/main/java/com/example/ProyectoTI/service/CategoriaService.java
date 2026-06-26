package com.example.ProyectoTI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProyectoTI.DTO.CategoriaDTO;
import com.example.ProyectoTI.model.Categoria;
import com.example.ProyectoTI.repository.CategoriaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodos(){
        log.info("Obteniendo todas las categorías");
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(Integer idCategoria){
        log.info("Obteniendo categoría con id: {}", idCategoria);
        return categoriaRepository.findById(idCategoria).orElse(null);
    }

    public Categoria guardarCategoria (Categoria categoria){
        log.info("Guardando categoría: {}", categoria.getNombreCategoria());
        return categoriaRepository.save(categoria);
    }

    private CategoriaDTO convertirADTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombreCategoria(categoria.getNombreCategoria());
        categoriaDTO.setDetalleCategoria(categoria.getDetalleCategoria());
        if (categoria.getIdCategoria() != null) {
            categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        } else {
            categoriaDTO.setIdCategoria(null);
        }
        return categoriaDTO;
    }

    public Categoria actualizarCategoria(Integer idCategoria, Categoria categoriaDTO){
        log.info("Actualizando categoría con el id: {}", idCategoria);
        Categoria categori = categoriaRepository.findById(idCategoria).orElseThrow(() -> new RuntimeException("No se encontró la categoría con id: " + idCategoria));
        if(categori.getNombreCategoria() != null){
            categori.setNombreCategoria(categoriaDTO.getNombreCategoria());
        }
        if(categori.getDetalleCategoria() != null){
            categori.setDetalleCategoria(categoriaDTO.getDetalleCategoria());
        }
        return categoriaRepository.save(categori);
    }

    public String eliminarCategoria(Integer idCategoria) {
        log.info("Eliminando categoría con id: {}", idCategoria);
        try{
            Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow(() -> new RuntimeException("No se encontró la categoría con id: " + idCategoria));
            categoriaRepository.delete(categoria);
            return "Categoría eliminada correctamente";
        } catch (Exception e) {
            return "Error al eliminar la categoría: " + e.getMessage();
        }
    }

}

