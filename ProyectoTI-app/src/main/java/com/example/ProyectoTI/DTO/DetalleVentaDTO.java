package com.example.ProyectoTI.DTO;
import lombok.Data;

@Data

public class DetalleVentaDTO {
    private Integer idVenta;
    private Integer idProducto;
    private Integer cantidadProducto;
    private Double precioUnitario;
}
