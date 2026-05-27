/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.exception;

/**
 *
 * @author jsanbaq
 */
public class ValidateEmail {
    private final static String EMAIL_REGEX = "/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$/";
    
    public static void validate(String email) throws ExceptionBadEmailFormatted {
        if (!email.matches(EMAIL_REGEX)) {
            throw new ExceptionBadEmailFormatted(
                    "El email debe ser válido"
            );
        }
    }
}
