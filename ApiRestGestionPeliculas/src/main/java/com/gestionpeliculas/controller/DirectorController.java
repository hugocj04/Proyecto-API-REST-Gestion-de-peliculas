package com.gestionpeliculas.controller;

import com.gestionpeliculas.dto.DirectorRequestDTO;
import com.gestionpeliculas.dto.DirectorResponseDTO;
import com.gestionpeliculas.dto.DirectorSimpleDTO;
import com.gestionpeliculas.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/directores")
@RequiredArgsConstructor
@Tag(name = "Directores", description = "API para gestion de directores")
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    @Operation(summary = "Obtener todos los directores", description = "Devuelve una lista con todos los directores registrados")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de directores obtenida correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DirectorResponseDTO.class)),
                    examples = @ExampleObject(
                            value = """
                    [
                      {
                        "id": 1,
                        "nombre": "Christopher Nolan",
                        "anioNacimiento": 1970
                      },
                      {
                        "id": 2,
                        "nombre": "Steven Spielberg",
                        "anioNacimiento": 1946
                      }
                    ]
                """
                    )
            )
    )
    public ResponseEntity<List<DirectorResponseDTO>> getAllDirectores() {
        List<DirectorResponseDTO> directores = directorService.findAll();
        return ResponseEntity.ok(directores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener director por ID", description = "Devuelve un director específico basado en su ID")
    @ApiResponse(
            responseCode = "200",
            description = "Director encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DirectorResponseDTO.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "id": 1,
                      "nombre": "Christopher Nolan",
                      "anioNacimiento": 1970
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Director no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Entidad No Encontrada",
                      "status": 404,
                      "detail": "Director no encontrado",
                      "instance": "/api/v1/directores/999"
                    }
                """
                    )
            )
    )
    public ResponseEntity<DirectorResponseDTO> getDirectorById(@PathVariable Long id) {
        DirectorResponseDTO director = directorService.findById(id);
        return ResponseEntity.ok(director);
    }

    @PostMapping("/create")
    @Operation(summary = "Crear Director", description = "Crea un nuevo director")
    @ApiResponse(
            responseCode = "201",
            description = "Director creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DirectorResponseDTO.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "id": 1,
                      "nombre": "Christopher Nolan",
                      "anioNacimiento": 1970
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Datos de entrada inválidos",
                      "status": 400,
                      "detail": "El nombre no puede estar vacío",
                      "instance": "/api/v1/directores"
                    }
                """
                    )
            )
    )
    public ResponseEntity<DirectorResponseDTO> createDirector(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del director a crear",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DirectorRequestDTO.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "nombre": "Christopher Nolan",
                          "anioNacimiento": 1970
                        }
                    """
                            )
                    )
            )
            @RequestBody DirectorRequestDTO directorRequestDTO
    ) {
        DirectorResponseDTO nuevoDirector = directorService.create(directorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDirector);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar director", description = "Actualiza los datos de un director existente")
    @ApiResponse(
            responseCode = "200",
            description = "Director actualizado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DirectorResponseDTO.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "id": 1,
                      "nombre": "Christopher Nolan Actualizado",
                      "anioNacimiento": 1971
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Director no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Entidad No Encontrada",
                      "status": 404,
                      "detail": "Director no encontrado",
                      "instance": "/api/v1/directores/999"
                    }
                """
                    )
            )
    )
    public ResponseEntity<DirectorResponseDTO> updateDirector(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos actualizados del director",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DirectorRequestDTO.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "nombre": "Christopher Nolan Actualizado",
                          "anioNacimiento": 1971
                        }
                    """
                            )
                    )
            )
            @RequestBody DirectorRequestDTO directorRequestDTO
    ) {
        DirectorResponseDTO directorActualizado = directorService.update(id, directorRequestDTO);
        return ResponseEntity.ok(directorActualizado);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar director", description = "Elimina un director existente")
    @ApiResponse(
            responseCode = "204",
            description = "Director eliminado correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Director no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Entidad No Encontrada",
                      "status": 404,
                      "detail": "Director no encontrado",
                      "instance": "/api/v1/directores/999"
                    }
                """
                    )
            )
    )
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}