package com.example.ProyectoTI.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "sucursal")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Sucursal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSucursal;
    
    @Column(nullable = false, length = 100)
    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @Column(nullable = false, length = 10)
    @NotBlank(message = "El número de calle es obligatorio")
    private String numeroCalle;

    @ManyToOne
    @JoinColumn(name = "idComuna")
    private Comuna comuna;

    @ManyToOne
    @JoinColumn(name = "idRegion")
    private Region region;

    @JsonIgnore
    @OneToMany(mappedBy = "sucursal")
    private List<Empleado> empleados;

}
