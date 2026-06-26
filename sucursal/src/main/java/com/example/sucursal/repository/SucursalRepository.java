package com.example.sucursal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sucursal.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    List<Sucursal> findByNombreComuna(String nombreComuna);

    List<Sucursal> findByNombreRegion(String nombreRegion);

    @Query("SELECT s FROM Sucursal s WHERE s.nombreRegion = :nombreRegion AND s.nombreComuna = :nombreComuna")
    List<Sucursal> buscaSucursales(@Param("nombreRegion") String nombreRegion, @Param("nombreComuna") String nombreComuna);

}