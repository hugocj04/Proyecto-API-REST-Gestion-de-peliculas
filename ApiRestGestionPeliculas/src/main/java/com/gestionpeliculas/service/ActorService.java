package com.gestionpeliculas.service;

import com.gestionpeliculas.dto.ActorDTO;
import com.gestionpeliculas.model.Actor;
import com.gestionpeliculas.repository.ActorRepository;
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
        actor.setNombre(actorDTO.nombre());

        Actor savedActor = actorRepository.save(actor);
        return convertToDTO(savedActor);
    }

    private ActorDTO convertToDTO(Actor actor) {
        return new ActorDTO(actor.getId(), actor.getNombre());
    }
}
