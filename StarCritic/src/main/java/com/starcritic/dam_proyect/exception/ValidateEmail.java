/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.exception;

/**
 * Validador del formato de un correo electrónico introducido por el usuario.
 *
 * @author Jesús Santos Baquero
 */
public class ValidateEmail {
    private final static String EMAIL_REGEX = "/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$/";

    /**
     * Comprobar que el email cumple el formato esperado.
     * @param email la dirección de correo a validar.
     * @throws ExceptionBadEmailFormatted si el email no cumple el patrón válido.
     */
    public static void validate(String email) throws ExceptionBadEmailFormatted {
        if (!email.matches(EMAIL_REGEX)) {
            throw new ExceptionBadEmailFormatted(
                    "El email debe ser válido"
            );
        }
    }
}
