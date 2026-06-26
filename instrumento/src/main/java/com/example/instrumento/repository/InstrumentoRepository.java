package com.example.instrumento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.instrumento.model.Instrumento;

@Repository
public interface InstrumentoRepository extends JpaRepository<Instrumento, Integer> {

    List<Instrumento> findByNombreCategoria(String nombreCategoria);

    List<Instrumento> findByNombreMarca(String nombreMarca);

    @Query("SELECT i FROM Instrumento i WHERE i.nombreCategoria = :nombreCategoria AND i.nombreMarca = :nombreMarca")
    List<Instrumento> buscaInstrumentos(@Param("nombreCategoria") String nombreCategoria, @Param("nombreMarca") String nombreMarca);

}