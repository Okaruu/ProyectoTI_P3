package com.example.ProyectoTI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ProyectoTI.model.Marca;
import com.example.ProyectoTI.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    List<Producto> findByNombreProducto(String nombreProducto);

    @Query("SELECT p FROM Producto p JOIN p.categoria c WHERE c.idCategoria = :id")
    List<Producto> buscarPorCategoria(@Param("id") Integer id);

    @Query("SELECT p FROM Producto p JOIN p.marca m WHERE m.idMarca = :id")
    List<Producto> buscarPorMarca(@Param("id") Integer id);

    boolean existsByMarca(Marca marca);
}
