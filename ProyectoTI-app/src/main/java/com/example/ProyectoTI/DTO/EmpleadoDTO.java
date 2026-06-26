package com.example.ProyectoTI.DTO;
import java.sql.Date;
import lombok.Data;
@Data

public class EmpleadoDTO {
    private Integer idEmpleado;
    private Integer idPuestoEmpleado;
    private Integer idSucursal;
    private String puestoEmpleado;
    private String nombreEmpleado;
    private String rutEmpleado;
    private Date fechaIngreso;
}
