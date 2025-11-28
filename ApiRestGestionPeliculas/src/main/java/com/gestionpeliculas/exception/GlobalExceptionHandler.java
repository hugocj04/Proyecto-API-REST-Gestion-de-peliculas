package com.gestionpeliculas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ProblemDetail handleEntidadNoEncontrada(EntidadNoEncontradaException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        problemDetail.setTitle("Entidad No Encontrada");
        problemDetail.setType(URI.create("about:blank"));

        return problemDetail;
    }

    @ExceptionHandler(PeliculaYaExisteException.class)
    public ProblemDetail handlePeliculaYaExiste(PeliculaYaExisteException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        problemDetail.setTitle("Película Ya Existe");
        problemDetail.setType(URI.create("about:blank"));

        return problemDetail;
    }

    @ExceptionHandler(ActorYaEnRepartoException.class)
    public ProblemDetail handleActorYaEnReparto(ActorYaEnRepartoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        problemDetail.setTitle("Actor Ya En Reparto");
        problemDetail.setType(URI.create("about:blank"));

        return problemDetail;
    }

    @ExceptionHandler(DirectorMenorEdadException.class)
    public ProblemDetail handleDirectorMenorEdad(DirectorMenorEdadException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        problemDetail.setTitle("Director Menor de Edad");
        problemDetail.setType(URI.create("about:blank"));

        return problemDetail;
    }
}
