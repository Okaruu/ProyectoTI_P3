package com.example.sucursal.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idSucursal")
    private Sucursal sucursal;

    @NotBlank(message = "El empleado debe contar con un nombre")
    @Column(nullable = false)
    private String nombreEmpleado;

    @NotBlank(message = "El empleado debe contar con un puesto")
    @Column(nullable = false)
    private String puestoEmpleado;

    @NotBlank(message = "El empleado debe contener un rut")
    @Column(unique = true, length = 13, nullable = false)
    private String rutEmpleado;

    @NotNull(message = "El empleado debe contar con una fecha de ingreso")
    @Column(nullable = false)
    private Date fechaIngreso;

    @NotBlank(message = "El tipo de empleado no puede estar vacio")
    @Column(nullable = false)
    private String tipoEmpleado;

    @NotNull(message = "El salario no puede estar vacio")
    @Column(nullable = false)
    private Double salario;

}