package com.gestionpeliculas.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PeliculaResponseDTO {

    private Long id;
    private String titulo;
    private String genero;
    private LocalDate fechaEstreno;
    private DirectorDTO director;
    private List<ActorDTO> actores = new ArrayList<>();

}
