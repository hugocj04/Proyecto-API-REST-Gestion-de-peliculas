package com.gestionpeliculas.service;

import com.gestionpeliculas.dto.ActorDTO;
import com.gestionpeliculas.dto.DirectorDTO;
import com.gestionpeliculas.dto.PeliculaRequestDTO;
import com.gestionpeliculas.dto.PeliculaResponseDTO;
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
            .map(this::convertToResponseDTO)
            .toList();
    }

    public PeliculaResponseDTO findById(Long id) {
        Pelicula pelicula = peliculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));
        return convertToResponseDTO(pelicula);
    }

    @Transactional
    public PeliculaResponseDTO create(PeliculaRequestDTO peliculaRequestDTO) {
        int edadDirector;
        Pelicula pelicula = new Pelicula();
        if (peliculaRepository.existsByTitulo(peliculaRequestDTO.getTitulo())) {
            throw new PeliculaYaExisteException("Ya existe una pelicula con ese titulo");
        }

        Director director = directorRepository.findById(peliculaRequestDTO.getDirectorId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));

        edadDirector = peliculaRequestDTO.getFechaEstreno().getYear() - director.getAnioNacimiento();
        if (edadDirector < 18) {
            throw new DirectorMenorEdadException("El director era menor de edad cuando se estreno la pelicula");
        }

        pelicula.setTitulo(peliculaRequestDTO.getTitulo());
        pelicula.setGenero(peliculaRequestDTO.getGenero());
        pelicula.setFechaEstreno(peliculaRequestDTO.getFechaEstreno());
        pelicula.setDirector(director);

        Pelicula savedPelicula = peliculaRepository.save(pelicula);
        return convertToResponseDTO(savedPelicula);
    }

    @Transactional
    public PeliculaResponseDTO update(Long id, PeliculaRequestDTO peliculaRequestDTO) {
        int edadDirector;
        Pelicula pelicula = peliculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));

        if (!pelicula.getTitulo().equals(peliculaRequestDTO.getTitulo()) && peliculaRepository.existsByTitulo(peliculaRequestDTO.getTitulo())) {
            throw new PeliculaYaExisteException("Ya existe una pelicula con ese titulo");
        }

        Director director = directorRepository.findById(peliculaRequestDTO.getDirectorId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));

        edadDirector = peliculaRequestDTO.getFechaEstreno().getYear() - director.getAnioNacimiento();
        if (edadDirector < 18) {
            throw new DirectorMenorEdadException("El director era menor de edad cuando se estrenó la");
        }

        pelicula.setTitulo(peliculaRequestDTO.getTitulo());
        pelicula.setGenero(peliculaRequestDTO.getGenero());
        pelicula.setFechaEstreno(peliculaRequestDTO.getFechaEstreno());
        pelicula.setDirector(director);

        Pelicula updatedPelicula = peliculaRepository.save(pelicula);
        return convertToResponseDTO(updatedPelicula);
    }

    public void delete(Long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new RuntimeException("Pelicula no encontrada");
        }
        peliculaRepository.deleteById(id);
    }

    @Transactional
    public void asignarActorAPelicula(Long peliculaId, Long actorId) {
        Pelicula pelicula = peliculaRepository.findById(peliculaId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Pelicula no encontrada"));

        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Actor no encontrado"));

        if (pelicula.getActores().contains(actor)) {
            throw new ActorYaEnRepartoExcepction("El actor ya está en el reparto de esta pelicula");
        }

        pelicula.addActor(actor);
        peliculaRepository.save(pelicula);
    }

    public PeliculaResponseDTO convertToResponseDTO(Pelicula pelicula) {
        PeliculaResponseDTO dto = new PeliculaResponseDTO();
        dto.setId(pelicula.getId());
        dto.setTitulo(pelicula.getTitulo());
        dto.setGenero(pelicula.getGenero());
        dto.setFechaEstreno(pelicula.getFechaEstreno());

        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setId(pelicula.getDirector().getId());
        directorDTO.setNombre(pelicula.getDirector().getNombre());
        directorDTO.setAnioNacimiento(pelicula.getDirector().getAnioNacimiento());
        dto.setDirector(directorDTO);

        List<ActorDTO> actoresDTO = pelicula.getActores().stream()
            .map(actor -> {
                ActorDTO actorDTO = new ActorDTO();
                actorDTO.setId(actor.getId());
                actorDTO.setNombre(actor.getNombre());
                return actorDTO;
            })
            .toList();
        dto.setActores(actoresDTO);

        return dto;
    }
}