package com.gestionpeliculas.service;

import com.gestionpeliculas.dto.ActorRequestDTO;
import com.gestionpeliculas.dto.ActorResponseDTO;
import com.gestionpeliculas.dto.ActorSimpleDTO;
import com.gestionpeliculas.exception.EntidadNoEncontradaException;
import com.gestionpeliculas.model.Actor;
import com.gestionpeliculas.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    public List<ActorResponseDTO> findAll() {
        return actorRepository.findAll().stream()
                .map(ActorResponseDTO::of)
                .toList();
    }

    public ActorResponseDTO findById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Actor no encontrado"));
        return ActorResponseDTO.of(actor);
    }

    public ActorResponseDTO create(ActorRequestDTO actorRequestDTO) {
        Actor actor = actorRequestDTO.toEntity();
        Actor savedActor = actorRepository.save(actor);
        return ActorResponseDTO.of(savedActor);
    }

    ActorSimpleDTO convertToSimpleDTO(Actor actor) {
        return ActorSimpleDTO.of(actor);
    }
}