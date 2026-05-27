/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.starcritic.dam_proyectspringboot.model.bd;

/**
 *
 * @author jsb
 */
public enum EstadoCertificacion {
    NO_SOLICITADA("NO_SOLICITADA"),
    PENDIENTE("PENDIENTE"),
    ACEPTADA("ACEPTADA"),
    RECHAZADA("RECHAZADA");

    private final String dbValue;

    EstadoCertificacion(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static EstadoCertificacion fromDbValue(String value) {
        if (value == null) {
            return null;
        }
        for (EstadoCertificacion estado : values()) {
            if (estado.dbValue.equals(value)) {
                return estado;
            }
        }
        return null;
    }
}
