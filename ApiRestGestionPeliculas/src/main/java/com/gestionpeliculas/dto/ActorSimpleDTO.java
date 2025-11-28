package com.gestionpeliculas.dto;

import com.gestionpeliculas.model.Actor;

public record ActorSimpleDTO(
        Long id,
        String nombre
) {
    public static ActorSimpleDTO of(Actor actor) {
        return new ActorSimpleDTO(
                actor.getId(),
                actor.getNombre()
        );
    }
}
