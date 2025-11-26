package com.gestionpeliculas.service;

import com.gestionpeliculas.dto.DirectorDTO;
import com.gestionpeliculas.model.Director;
import com.gestionpeliculas.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    public List<DirectorDTO> findAll() {
        return directorRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public DirectorDTO findById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));
        return convertToDTO(director);
    }

    public DirectorDTO create(DirectorDTO directorDTO) {
        Director director = new Director();
        director.setNombre(directorDTO.getNombre());
        director.setAnioNacimiento(directorDTO.getAnioNacimiento());

        Director savedDirector = directorRepository.save(director);
        return convertToDTO(savedDirector);
    }

    public DirectorDTO update(Long id, DirectorDTO directorDTO) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));

        director.setNombre(directorDTO.getNombre());
        director.setAnioNacimiento(directorDTO.getAnioNacimiento());

        Director updatedDirector = directorRepository.save(director);
        return convertToDTO(updatedDirector);
    }

    public void delete(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new EntidadNoEncontradaException("Director no encontrado");
        }

        directorRepository.deleteById(id);
    }

    public DirectorDTO convertToDTO(Director director) {
        DirectorDTO dto = new DirectorDTO();
        dto.setId(director.getId());
        dto.setNombre(director.getNombre());
        dto.setAnioNacimiento(director.getAnioNacimiento());
        return dto;
    }
}