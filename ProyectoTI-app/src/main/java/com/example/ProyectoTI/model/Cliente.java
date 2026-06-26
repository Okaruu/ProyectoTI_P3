package com.example.ProyectoTI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor 

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;
    
    @NotBlank (message="El rut del cliente no puede estar vacio")
    @Column(unique=true, length = 13, nullable = false)
    private String rutCliente;
    
    @NotBlank(message="el nombre del cliente no puede estar vacio")
    @Column(nullable = false, length = 35)
    @Size(min = 2, max = 35)
    private String nombreCliente;

    @NotBlank (message="el telefono del cliente no puede estar vacio")
    @Column(length = 12, nullable = false)
    private String telefonoCliente;

    @NotBlank (message="el email del cliente no puede estar vacio")
    @Column(nullable = false)
    private String emailCliente;

}
