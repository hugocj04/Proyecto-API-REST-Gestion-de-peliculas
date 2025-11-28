package com.gestionpeliculas.dto;

import com.gestionpeliculas.model.Pelicula;

import java.time.LocalDate;
import java.util.List;

public record PeliculaResponseDTO(
        Long id,
        String titulo,
        String genero,
        LocalDate fechaEstreno,
        DirectorSimpleDTO director,
        List<ActorSimpleDTO> actores
) {
    public static PeliculaResponseDTO of(Pelicula pelicula) {
        return new PeliculaResponseDTO(
                pelicula.getId(),
                pelicula.getTitulo(),
                pelicula.getGenero(),
                pelicula.getFechaEstreno(),
                DirectorSimpleDTO.of(pelicula.getDirector()),
                pelicula.getActores().stream()
                        .map(ActorSimpleDTO::of)
                        .toList()
        );
    }}
