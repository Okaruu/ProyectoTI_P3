package com.example.venta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleVenta;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    private Venta venta;

    @NotBlank(message = "El detalle debe indicar el nombre del producto")
    @Column(nullable = false)
    private String nombreProducto;

    @Min(value = 1, message = "La cantidad de producto debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidadProducto;

    @Column(nullable = false)
    private Double precioUnitario;

}