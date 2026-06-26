package com.example.venta.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenta;

    @NotBlank(message = "El rut del cliente no puede estar vacio")
    @Column(length = 13, nullable = false)
    private String rutCliente;

    @NotBlank(message = "El nombre del cliente no puede estar vacio")
    @Column(length = 35, nullable = false)
    private String nombreCliente;

    @Column(length = 12)
    private String telefonoCliente;

    @Column
    private String emailCliente;

    @NotBlank(message = "El rut del empleado no puede estar vacio")
    @Column(length = 13, nullable = false)
    private String rutEmpleado;

    @NotBlank(message = "El nombre del empleado no puede estar vacio")
    @Column(nullable = false)
    private String nombreEmpleado;

    @NotNull(message = "La venta debe tener una fecha")
    @Column(nullable = false)
    private Date fechaVenta;

    @NotNull(message = "La venta debe tener un precio final")
    @Column(nullable = false)
    private Double precioFinal;

    @JsonIgnore
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles;

}