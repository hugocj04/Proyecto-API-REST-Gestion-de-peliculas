package com.gestionpeliculas.service;

import com.gestionpeliculas.dto.ActorSimpleDTO;
import com.gestionpeliculas.dto.DirectorSimpleDTO;
import com.gestionpeliculas.dto.PeliculaRequestDTO;
import com.gestionpeliculas.dto.PeliculaResponseDTO;
import com.gestionpeliculas.exception.ActorYaEnRepartoException;
import com.gestionpeliculas.exception.DirectorMenorEdadException;
import com.gestionpeliculas.exception.EntidadNoEncontradaException;
import com.gestionpeliculas.exception.PeliculaYaExisteException;
import com.gestionpeliculas.model.Actor;
import com.gestionpeliculas.model.Director;
import com.gestionpeliculas.model.Pelicula;
import com.gestionpeliculas.repository.ActorRepository;
import com.gestionpeliculas.repository.DirectorRepository;
import com.gestionpeliculas.repository.PeliculaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;

    public List<PeliculaResponseDTO> findAll() {
        return peliculaRepository.findAll().stream()
                .map(PeliculaResponseDTO::of)
                .toList();
    }

    public PeliculaResponseDTO findById(Long id) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Pelicula no encontrada"));
        return PeliculaResponseDTO.of(pelicula);
    }

    @Transactional
    public PeliculaResponseDTO create(PeliculaRequestDTO peliculaRequestDTO) {
        int edadDirector;
        Pelicula pelicula = new Pelicula();
        if (peliculaRepository.existsByTitulo(peliculaRequestDTO.titulo())) {
            throw new PeliculaYaExisteException("Ya existe una pelicula con ese titulo");
        }

        Director director = directorRepository.findById(peliculaRequestDTO.directorId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));

        edadDirector = peliculaRequestDTO.fechaEstreno().getYear() - director.getAnioNacimiento();
        if (edadDirector < 18) {
            throw new DirectorMenorEdadException("El director era menor de edad cuando se estreno la pelicula");
        }

        pelicula.setTitulo(peliculaRequestDTO.titulo());
        pelicula.setGenero(peliculaRequestDTO.genero());
        pelicula.setFechaEstreno(peliculaRequestDTO.fechaEstreno());
        pelicula.setDirector(director);

        Pelicula savedPelicula = peliculaRepository.save(pelicula);
        return PeliculaResponseDTO.of(savedPelicula);
    }

    @Transactional
    public PeliculaResponseDTO update(Long id, PeliculaRequestDTO peliculaRequestDTO) {
        int edadDirector;
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Pelicula no encontrada"));

        if (!pelicula.getTitulo().equals(peliculaRequestDTO.titulo()) &&
                peliculaRepository.existsByTitulo(peliculaRequestDTO.titulo())) {
            throw new PeliculaYaExisteException("Ya existe una pelicula con ese titulo");
        }

        Director director = directorRepository.findById(peliculaRequestDTO.directorId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));

        edadDirector = peliculaRequestDTO.fechaEstreno().getYear() - director.getAnioNacimiento();
        if (edadDirector < 18) {
            throw new DirectorMenorEdadException("El director era menor de edad cuando se estrenó la");
        }

        pelicula.setTitulo(peliculaRequestDTO.titulo());
        pelicula.setGenero(peliculaRequestDTO.genero());
        pelicula.setFechaEstreno(peliculaRequestDTO.fechaEstreno());
        pelicula.setDirector(director);

        Pelicula updatedPelicula = peliculaRepository.save(pelicula);
        return PeliculaResponseDTO.of(updatedPelicula);
    }

    public void delete(Long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new EntidadNoEncontradaException("Pelicula no encontrada");
        }
        peliculaRepository.deleteById(id);
    }

    @Transactional
    public void asignarActorAPelicula(Long peliculaId, Long actorId) {
        Pelicula pelicula = peliculaRepository.findById(peliculaId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Película no encontrada"));

        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Actor no encontrado"));

        if (pelicula.getActores().contains(actor)) {
            throw new ActorYaEnRepartoException("El actor ya está en el reparto de esta película");
        }

        pelicula.addActor(actor);
    }
}