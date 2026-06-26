package com.example.venta.DTO;

import lombok.Data;

@Data
public class DetalleVentaDTO {

    private String nombreProducto;
    private Integer cantidadProducto;
    private Double precioUnitario;

}