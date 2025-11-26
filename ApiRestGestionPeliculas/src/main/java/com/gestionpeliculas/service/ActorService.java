package com.gestionpeliculas.service;

import com.gestionpeliculas.dto.ActorDTO;
import com.gestionpeliculas.model.Actor;
import com.gestionpeliculas.repository.ActorRepository;
import com.gestionpeliculas.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    public List<ActorDTO> findAll() {
        return actorRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ActorDTO findById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor no encontrado"));

        return convertToDTO(actor);
    }

    public ActorDTO create(ActorDTO actorDTO) {
        Actor actor = new Actor();
        actor.setNombre(actorDTO.getNombre());

        Actor savedActor = actorRepository.save(actor);
        return convertToDTO(savedActor);
    }

    public ActorDTO convertToDTO(Actor actor) {
        ActorDTO dto = new ActorDTO();
        dto.setId(actor.getId());
        dto.setNombre(actor.getNombre());

        return dto;
    }
}
