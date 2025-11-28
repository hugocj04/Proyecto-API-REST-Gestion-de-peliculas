package com.gestionpeliculas.dto;

import com.gestionpeliculas.model.Director;

public record DirectorRequestDTO(
        String nombre,
        Integer anioNacimiento
) {
    public Director toEntity() {
        Director director = new Director();
        director.setNombre(this.nombre());
        director.setAnioNacimiento(this.anioNacimiento());
        return director;
    }
}