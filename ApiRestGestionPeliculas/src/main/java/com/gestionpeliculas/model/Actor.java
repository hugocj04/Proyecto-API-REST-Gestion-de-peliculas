package com.gestionpeliculas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Actor {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private List<Pelicula> peliculas;
}
