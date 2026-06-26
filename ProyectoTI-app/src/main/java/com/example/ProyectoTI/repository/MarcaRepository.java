package com.example.ProyectoTI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoTI.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer>{

    List<Marca> findByIdMarca(Integer idMarca);

}
