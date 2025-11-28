package com.gestionpeliculas.dto;

import com.gestionpeliculas.model.Actor;

public record ActorRequestDTO(
        String nombre
) {
    public Actor toEntity() {
        Actor actor = new Actor();
        actor.setNombre(this.nombre());
        return actor;
    }
}
