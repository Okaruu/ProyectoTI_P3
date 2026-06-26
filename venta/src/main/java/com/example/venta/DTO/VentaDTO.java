package com.example.venta.DTO;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class VentaDTO {

    private Integer idVenta;
    private String rutCliente;
    private String nombreCliente;
    private String telefonoCliente;
    private String emailCliente;
    private String rutEmpleado;
    private String nombreEmpleado;
    private Date fechaVenta;
    private Double precioFinal;
    private List<DetalleVentaDTO> detalles;

}