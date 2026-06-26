package com.example.ProyectoTI.DTO;

import lombok.Data;
import java.sql.Date;

@Data
public class VentaDTO {

    private Integer idVenta;
    private Integer idCliente;
    private Integer idEmpleado;
    private Date fechaVenta;
    private Double precioFinal;
    private Integer idSucursal;
    private Integer idProducto;
    private DetalleVentaDTO detalleVenta;
}
