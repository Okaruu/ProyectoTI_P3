package com.example.ProyectoTI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoTI.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {

}
