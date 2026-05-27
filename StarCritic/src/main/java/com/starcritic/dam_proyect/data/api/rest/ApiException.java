package com.starcritic.dam_proyect.data.api.rest;

/**
 * Error no comprobado al comunicarse con la API REST. Se propaga hasta el
 * callback de error de BackgroundWork para que la UI lo gestione.
 *
 * @author Jesús Santos Baquero
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
