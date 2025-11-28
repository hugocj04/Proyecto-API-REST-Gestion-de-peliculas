package com.gestionpeliculas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "actores", fetch = FetchType.LAZY)
    private List<Pelicula> peliculas = new ArrayList<>();
}