package com.example.sucursal.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class EmpleadoDTO {

    private String nombreEmpleado;
    private String puestoEmpleado;
    private String rutEmpleado;
    private Date fechaIngreso;
    private String tipoEmpleado;
    private Double salario;

}