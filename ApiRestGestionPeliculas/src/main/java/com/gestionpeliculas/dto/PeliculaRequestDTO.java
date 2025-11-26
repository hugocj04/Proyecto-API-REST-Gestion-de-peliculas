package com.gestionpeliculas.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PeliculaRequestDTO {

    private String titulo;
    private String genero;
    private LocalDate fechaEstreno;
    private Long directorId;

}
