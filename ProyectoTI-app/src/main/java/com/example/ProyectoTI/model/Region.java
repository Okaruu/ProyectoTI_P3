package com.example.ProyectoTI.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "region")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegion;
    
    @NotBlank (message="el nombre de la region no puede estar vacio")
    @Column(nullable = false)
    private String nombreRegion;
    
}
