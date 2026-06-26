package com.example.instrumento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instrumento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instrumento")
    private Integer idInstrumento;

    @NotBlank(message = "El instrumento debe contar con un nombre")
    @Column(name = "nombre_instrumento", nullable = false)
    private String nombreInstrumento;

    @Column(name = "descripcion_instrumento", nullable = false)
    private String descripcionInstrumento;

    @Min(value = 0, message = "El stock del instrumento no puede ser negativo")
    @Column(name = "stock_instrumento", nullable = false)
    private Integer stockInstrumento;

    @NotNull(message = "El instrumento debe contar con un precio")
    @Min(value = 0)
    @Column(name = "precio_instrumento", nullable = false)
    private Double precioInstrumento;

    @NotBlank(message = "El instrumento debe contar con una categoria")
    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    @Column(name = "detalle_categoria", length = 500)
    private String detalleCategoria;

    @NotBlank(message = "El instrumento debe contar con una marca")
    @Column(name = "nombre_marca", nullable = false)
    private String nombreMarca;

}