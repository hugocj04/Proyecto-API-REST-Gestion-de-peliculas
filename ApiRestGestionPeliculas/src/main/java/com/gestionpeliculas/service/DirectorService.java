package com.gestionpeliculas.service;

import com.gestionpeliculas.dto.DirectorDTO;
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
        director.setNombre(directorDTO.nombre());
        director.setAnioNacimiento(directorDTO.anioNacimiento());

        Director savedDirector = directorRepository.save(director);
        return convertToDTO(savedDirector);
    }

    public DirectorDTO update(Long id, DirectorDTO directorDTO) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Director no encontrado"));

        director.setNombre(directorDTO.nombre());
        director.setAnioNacimiento(directorDTO.anioNacimiento());

        Director updatedDirector = directorRepository.save(director);
        return convertToDTO(updatedDirector);
    }

    public void delete(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new EntidadNoEncontradaException("Director no encontrado");
        }

        directorRepository.deleteById(id);
    }

    private DirectorDTO convertToDTO(Director director) {
        return new DirectorDTO(director.getId(), director.getNombre(), director.getAnioNacimiento());
    }
}