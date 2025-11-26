package com.gestionpeliculas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String genero;
    private LocalDate fechaEstreno;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany
    @JoinTable(
            name = "pelicula_actor",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )

    private List<Actor> actores = new ArrayList<>();

    public void addActor(Actor actor) {
        this.actores.add(actor);
        actor.getPeliculas().add(this);
    }

    public void removeActor(Actor actor) {
        this.actores.remove(actor);
        actor.getPeliculas().remove(this);
    }
}
