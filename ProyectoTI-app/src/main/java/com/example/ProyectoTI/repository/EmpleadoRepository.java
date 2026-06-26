package com.example.ProyectoTI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ProyectoTI.model.Empleado;

@Repository

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
    List<Empleado> findByIdEmpleado(Integer idEmpleado);

    @Query("Select e FROM Empleado e WHERE e.idEmpleado = :idEmpleado")
    List<Empleado> buscaIdEmpleados(@Param("idEmpleado") Integer idEmpleado);

}
