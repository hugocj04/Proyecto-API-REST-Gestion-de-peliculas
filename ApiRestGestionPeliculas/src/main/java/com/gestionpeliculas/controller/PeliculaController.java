package com.gestionpeliculas.controller;

import com.gestionpeliculas.dto.PeliculaRequestDTO;
import com.gestionpeliculas.dto.PeliculaResponseDTO;
import com.gestionpeliculas.service.PeliculaService;
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
@RequestMapping("api/v1/peliculas")
@RequiredArgsConstructor
@Tag(name = "Peliculas", description = "API para gestion de peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @GetMapping
    @Operation(summary = "Obtener todas las películas", description = "Devuelve una lista con todas las películas registradas")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de películas obtenida correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PeliculaResponseDTO.class)),
                    examples = @ExampleObject(
                            value = """
                    [
                      {
                        "id": 1,
                        "titulo": "Inception",
                        "genero": "Ciencia Ficción",
                        "fechaEstreno": "2010-07-16",
                        "director": {
                          "id": 1,
                          "nombre": "Christopher Nolan",
                          "anioNacimiento": 1970
                        },
                        "actores": [
                          {
                            "id": 1,
                            "nombre": "Leonardo DiCaprio"
                          }
                        ]
                      }
                    ]
                """
                    )
            )
    )
    public ResponseEntity<List<PeliculaResponseDTO>> getAllPeliculas() {
        List<PeliculaResponseDTO> peliculas = peliculaService.findAll();
        return ResponseEntity.ok(peliculas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener película por ID", description = "Devuelve una película específica con su reparto completo")
    @ApiResponse(
            responseCode = "200",
            description = "Película encontrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PeliculaResponseDTO.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "id": 1,
                      "titulo": "Inception",
                      "genero": "Ciencia Ficción",
                      "fechaEstreno": "2010-07-16",
                      "director": {
                        "id": 1,
                        "nombre": "Christopher Nolan",
                        "anioNacimiento": 1970
                      },
                      "actores": [
                        {
                          "id": 1,
                          "nombre": "Leonardo DiCaprio"
                        },
                        {
                          "id": 2,
                          "nombre": "Marion Cotillard"
                        }
                      ]
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Película no encontrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Entidad No Encontrada",
                      "status": 404,
                      "detail": "Película no encontrada",
                      "instance": "/api/v1/peliculas/999"
                    }
                """
                    )
            )
    )
    public ResponseEntity<PeliculaResponseDTO> getPeliculaById(@PathVariable Long id) {
        PeliculaResponseDTO pelicula = peliculaService.findById(id);
        return ResponseEntity.ok(pelicula);
    }

    @PostMapping
    @Operation(summary = "Crear Película", description = "Crea una nueva película. Requiere el ID de un director existente")
    @ApiResponse(
            responseCode = "201",
            description = "Película creada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PeliculaResponseDTO.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "id": 1,
                      "titulo": "Inception",
                      "genero": "Ciencia Ficción",
                      "fechaEstreno": "2010-07-16",
                      "director": {
                        "id": 1,
                        "nombre": "Christopher Nolan",
                        "anioNacimiento": 1970
                      },
                      "actores": []
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Director menor de edad",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Director Menor de Edad",
                      "status": 400,
                      "detail": "El director era menor de edad cuando se estrenó la película",
                      "instance": "/api/v1/peliculas"
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Película ya existe",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Película Ya Existe",
                      "status": 409,
                      "detail": "Ya existe una película con ese título",
                      "instance": "/api/v1/peliculas"
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
                      "instance": "/api/v1/peliculas"
                    }
                """
                    )
            )
    )
    public ResponseEntity<PeliculaResponseDTO> createPelicula(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos de la película a crear",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PeliculaRequestDTO.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "titulo": "Inception",
                          "genero": "Ciencia Ficción",
                          "fechaEstreno": "2010-07-16",
                          "directorId": 1
                        }
                    """
                            )
                    )
            )
            @RequestBody PeliculaRequestDTO peliculaRequestDTO
    ) {
        PeliculaResponseDTO nuevaPelicula = peliculaService.create(peliculaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPelicula);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar película", description = "Actualiza los datos de una película existente")
    @ApiResponse(
            responseCode = "200",
            description = "Película actualizada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PeliculaResponseDTO.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "id": 1,
                      "titulo": "Inception Actualizada",
                      "genero": "Thriller",
                      "fechaEstreno": "2010-07-16",
                      "director": {
                        "id": 1,
                        "nombre": "Christopher Nolan",
                        "anioNacimiento": 1970
                      },
                      "actores": []
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Película o director no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Entidad No Encontrada",
                      "status": 404,
                      "detail": "Película no encontrada",
                      "instance": "/api/v1/peliculas/999"
                    }
                """
                    )
            )
    )
    public ResponseEntity<PeliculaResponseDTO> updatePelicula(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos actualizados de la película",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PeliculaRequestDTO.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "titulo": "Inception Actualizada",
                          "genero": "Thriller",
                          "fechaEstreno": "2010-07-16",
                          "directorId": 1
                        }
                    """
                            )
                    )
            )
            @RequestBody PeliculaRequestDTO peliculaRequestDTO
    ) {
        PeliculaResponseDTO peliculaActualizada = peliculaService.update(id, peliculaRequestDTO);
        return ResponseEntity.ok(peliculaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar película", description = "Elimina una película existente")
    @ApiResponse(
            responseCode = "204",
            description = "Película eliminada correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Película no encontrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Entidad No Encontrada",
                      "status": 404,
                      "detail": "Película no encontrada",
                      "instance": "/api/v1/peliculas/999"
                    }
                """
                    )
            )
    )
    public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {
        peliculaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{peliculaId}/actores/{actorId}")
    @Operation(summary = "Asignar actor a película", description = "Asigna un actor al reparto de una película")
    @ApiResponse(
            responseCode = "200",
            description = "Actor asignado correctamente al reparto"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Película o actor no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Entidad No Encontrada",
                      "status": 404,
                      "detail": "Película no encontrada",
                      "instance": "/api/v1/peliculas/999/actores/1"
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Actor ya en reparto",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Actor Ya En Reparto",
                      "status": 409,
                      "detail": "El actor ya está en el reparto de esta película",
                      "instance": "/api/v1/peliculas/1/actores/1"
                    }
                """
                    )
            )
    )
    public ResponseEntity<Void> asignarActorAPelicula(
            @PathVariable Long peliculaId,
            @PathVariable Long actorId
    ) {
        peliculaService.asignarActorAPelicula(peliculaId, actorId);
        return ResponseEntity.ok().build();
    }

}
