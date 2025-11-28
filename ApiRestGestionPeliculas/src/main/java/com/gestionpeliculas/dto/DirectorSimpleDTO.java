package com.gestionpeliculas.dto;

import com.gestionpeliculas.model.Director;

public record DirectorSimpleDTO(
        Long id,
        String nombre,
        Integer anioNacimiento
) {
    public static DirectorSimpleDTO of(Director director) {
        return new DirectorSimpleDTO(
                director.getId(),
                director.getNombre(),
                director.getAnioNacimiento()
        );
    }
}
