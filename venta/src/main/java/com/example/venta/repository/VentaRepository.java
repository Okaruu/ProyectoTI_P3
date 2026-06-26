package com.example.venta.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.venta.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    List<Venta> findByRutCliente(String rutCliente);

    List<Venta> findByRutEmpleado(String rutEmpleado);

    @Query("SELECT v FROM Venta v WHERE v.fechaVenta = :fechaVenta")
    List<Venta> buscaVentasPorFecha(@Param("fechaVenta") Date fechaVenta);

}