package com.example.ProyectoTI.DTO;

import lombok.Data;

@Data

public class ProductoDTO {
    private Integer idProducto;
    private Integer idCategoria;
    private Integer idMarca;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioProducto;
    
}
