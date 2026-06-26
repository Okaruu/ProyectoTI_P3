package com.example.ProyectoTI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ProyectoTI.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

    List<Cliente> findByIdCliente(Integer idCliente);

    @Query("Select c FROM Cliente c WHERE c.rutCliente = :rutCLiente")
    List<Cliente> buscarPorRut(@Param("rutCliente") String rutCliente);

}
