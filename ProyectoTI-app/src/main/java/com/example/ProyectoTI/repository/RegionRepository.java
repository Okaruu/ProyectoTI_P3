package com.example.ProyectoTI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProyectoTI.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>{

}
