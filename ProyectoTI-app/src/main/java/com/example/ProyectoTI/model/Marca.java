package com.example.ProyectoTI.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "marca")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMarca;

    @NotBlank (message="el Nombre de la marca no puede estar vacio")
    @Column(nullable = false)
    private String nombreMarca;
    
}

