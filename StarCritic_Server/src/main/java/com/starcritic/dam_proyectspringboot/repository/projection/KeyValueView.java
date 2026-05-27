package com.starcritic.dam_proyectspringboot.repository.projection;

/**
 * Proyeccion clave-valor para las consultas agregadas de estadisticas.
 * Las consultas nativas deben aliasar las columnas como {@code k} y {@code v}.
 */
public interface KeyValueView {
    String getK();
    Double getV();
}
