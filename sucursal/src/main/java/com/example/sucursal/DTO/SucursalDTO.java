package com.example.sucursal.DTO;

import java.util.List;

import lombok.Data;

@Data
public class SucursalDTO {

    private Integer idSucursal;
    private String calle;
    private String numeroCalle;
    private String nombreComuna;
    private String nombreRegion;
    private List<EmpleadoDTO> empleados;

}