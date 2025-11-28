package com.gestionpeliculas.service;

import com.gestionpeliculas.dto.DirectorRequestDTO;
import com.gestionpeliculas.dto.DirectorResponseDTO;
import com.gestionpeliculas.dto.DirectorSimpleDTO;
import com.gestionpeliculas.exception.EntidadNoEncontradaException;
import com.gestionpeliculas.model.Director;
import com.gestionpeliculas.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    public List<DirectorResponseDTO> findAll() {
        return directorRepository.findAll().stream()
                .map(DirectorResponseDTO::of)
                .toList();
    }

    public DirectorResponseDTO findById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));
        return DirectorResponseDTO.of(director);
    }

    public DirectorResponseDTO create(DirectorRequestDTO directorRequestDTO) {
        Director director = directorRequestDTO.toEntity();
        Director savedDirector = directorRepository.save(director);
        return DirectorResponseDTO.of(savedDirector);
    }

    public DirectorResponseDTO update(Long id, DirectorRequestDTO directorRequestDTO) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));

        director.setNombre(directorRequestDTO.nombre());
        director.setAnioNacimiento(directorRequestDTO.anioNacimiento());

        Director updatedDirector = directorRepository.save(director);
        return DirectorResponseDTO.of(updatedDirector);
    }

    public void delete(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));

        directorRepository.delete(director);
    }
}