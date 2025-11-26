package com.gestionpeliculas.exception;

public class PeliculaYaExisteException extends RuntimeException {
    public PeliculaYaExisteException(String message) {
        super(message);
    }
}
