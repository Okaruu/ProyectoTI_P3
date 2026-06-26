package com.example.ProyectoTI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table (name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    @ManyToOne
    @JoinColumn(name = "idTipoEmpleado")
    private TipoEmpleado tipoEmpleado;

    @ManyToOne
    @JoinColumn(name = "idSucursal")
    private Sucursal sucursal;

    @Column(nullable = false)
    @NotBlank(message = "El empleado debe contar con un nombre")
    private String nombreEmpleado;

    @Column(nullable = false)
    @NotBlank(message = "El empleado debe contar con un puesto.")
    private String puestoEmpleado;

    @Column(unique=true, length = 13, nullable = false)
    @NotBlank(message = "El empleado debe contener un rut.")
    private String rutEmpleado;

    @Column(nullable = false)
    @NotNull(message = "El empleado debe contar con una fecha de ignreso.")
    private Date fechaIngreso;

}
