package com.gestionpeliculas.controller;

import com.gestionpeliculas.dto.ActorDTO;
import com.gestionpeliculas.service.ActorService;
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
@RequestMapping("api/v1/actores")
@RequiredArgsConstructor
@Tag(name = "Actores", description = "API para gestion de actores")
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    @Operation(summary = "Obtener todos los actores", description = "Devuelve una lista con todos los actores registrados")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de actores obtenida correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ActorDTO.class)),
                    examples = @ExampleObject(
                            value = """
                    [
                      {
                        "id": 1,
                        "nombre": "Leonardo DiCaprio"
                      },
                      {
                        "id": 2,
                        "nombre": "Marion Cotillard"
                      },
                      {
                        "id": 3,
                        "nombre": "Tom Hardy"
                      }
                    ]
                """
                    )
            )
    )
    public ResponseEntity<List<ActorDTO>> getAllActores() {
        List<ActorDTO> actores = actorService.findAll();
        return ResponseEntity.ok(actores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener actor por ID", description = "Devuelve un actor específico basado en su ID")
    @ApiResponse(
            responseCode = "200",
            description = "Actor encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ActorDTO.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "id": 1,
                      "nombre": "Leonardo DiCaprio"
                    }
                """
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Actor no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "type": "about:blank",
                      "title": "Entidad No Encontrada",
                      "status": 404,
                      "detail": "Actor no encontrado",
                      "instance": "/api/v1/actores/999"
                    }
                """
                    )
            )
    )
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        ActorDTO actor = actorService.findById(id);
        return ResponseEntity.ok(actor);
    }

    @PostMapping
    @Operation(summary = "Crear Actor", description = "Crea un nuevo actor")
    @ApiResponse(
            responseCode = "201",
            description = "Actor creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ActorDTO.class),
                    examples = @ExampleObject(
                            value = """
                    {
                      "id": 1,
                      "nombre": "Leonardo DiCaprio"
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
                      "instance": "/api/v1/actores"
                    }
                """
                    )
            )
    )
    public ResponseEntity<ActorDTO> createActor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del actor a crear",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ActorDTO.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "nombre": "Leonardo DiCaprio"
                        }
                    """
                            )
                    )
            )
            @RequestBody ActorDTO actorDTO
    ) {
        ActorDTO nuevoActor = actorService.create(actorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoActor);
    }

}
