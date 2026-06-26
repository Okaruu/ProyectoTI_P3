package com.example.ProyectoTI.DTO;

import java.util.List;

import lombok.Data;
@Data

public class SucursalDTO {

    private Integer idSucursal;
    private Integer idComuna;
    private Integer idRegion;
    private String calle;
    private String numeroCalle;
    private List<String> empleados;

}
