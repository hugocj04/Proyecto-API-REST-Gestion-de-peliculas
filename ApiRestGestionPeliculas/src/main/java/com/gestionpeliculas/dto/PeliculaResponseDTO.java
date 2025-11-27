package com.gestionpeliculas.dto;

import java.time.LocalDate;
import java.util.List;

public record PeliculaResponseDTO(
        Long id,
        String titulo,
        String genero,
        LocalDate fechaEstreno,
        DirectorDTO director,
        List<ActorDTO> actores
) {
    public PeliculaResponseDTO {
        if (actores == null) {
            actores = List.of();
        }
    }
}
