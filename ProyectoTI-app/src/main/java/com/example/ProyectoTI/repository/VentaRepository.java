package com.example.ProyectoTI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ProyectoTI.model.Venta;

@Repository

public interface VentaRepository extends JpaRepository<Venta, Integer>{
    List<Venta> findByCliente(String nombreCliente);

    List<Venta> findByEmpleado_IdEmpleado(Integer idEmpleado);

    @Query("Select v FROM Venta v WHERE v.idVenta = :idVenta")
    List<Venta> buscaidVentas(@Param("idVenta") Integer idVenta);

}