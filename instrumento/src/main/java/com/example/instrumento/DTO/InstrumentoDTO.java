package com.example.instrumento.DTO;

import lombok.Data;

@Data
public class InstrumentoDTO {

    private Integer idInstrumento;
    private String nombreInstrumento;
    private String descripcionInstrumento;
    private Integer stockInstrumento;
    private Double precioInstrumento;
    private String nombreCategoria;
    private String detalleCategoria;
    private String nombreMarca;

}