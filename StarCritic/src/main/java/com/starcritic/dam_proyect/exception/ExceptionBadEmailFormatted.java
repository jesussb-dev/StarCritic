/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.exception;

/**
 * Excepción comprobada lanzada por {@link ValidateEmail} cuando el formato
 * del email introducido no es válido.
 *
 * @author Jesús Santos Baquero
 */
public class ExceptionBadEmailFormatted extends Exception {
    
    
    public ExceptionBadEmailFormatted(String message){
        super(message);
    }
}
