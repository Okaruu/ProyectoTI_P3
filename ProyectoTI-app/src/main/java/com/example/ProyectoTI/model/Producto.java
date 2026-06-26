package com.example.ProyectoTI.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table (name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(nullable = false)
    @NotBlank(message = "El producto debe contar con un nombre.")
    private String nombreProducto;

    @Column(nullable = false)
    private String descripcionProducto;

    @Column(nullable = false)
    @Min(value = 0, message = "El stock del producto no puede ser negativo.")
    private Integer stockProducto;

    @JsonIgnore
    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detalles;

    @Column(nullable = false)
    @NotNull(message = "El producto debe contar con un precio.")
    @Min(value = 0)
    private Double precioProducto;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "idMarca")
    private Marca marca;

}
