package com.example.ProyectoTI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "tipoEmpleado")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TipoEmpleado {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idTipoEmpleado;

    @Column(nullable = false, length = 100)
    @NotBlank (message ="El nombre del puesto no puede estar vacio")
    private String puesto;

    @Column(nullable = false, length = 100)
    @NotBlank (message="El tipo de empleado no puede estar vacio")
    private String tipoEmpleado;
    
    @Column(nullable = false, precision = 10, scale = 0)
    @NotNull (message= "El salario del empleado no puede estar vacio")
    private Double salario;

}
