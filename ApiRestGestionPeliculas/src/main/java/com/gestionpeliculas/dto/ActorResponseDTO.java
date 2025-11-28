package com.gestionpeliculas.dto;

import com.gestionpeliculas.model.Actor;

public record ActorResponseDTO(
        Long id,
        String nombre
) {
    public static ActorResponseDTO of(Actor actor) {
        return new ActorResponseDTO(
                actor.getId(),
                actor.getNombre()
        );
    }
}
