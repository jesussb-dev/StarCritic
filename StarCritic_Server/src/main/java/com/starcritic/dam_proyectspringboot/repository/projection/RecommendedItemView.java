package com.starcritic.dam_proyectspringboot.repository.projection;

/**
 * Proyeccion de una recomendacion. Las consultas nativas deben aliasar las
 * columnas con los nombres de propiedad (idContenido, posterKey, apiId, etc.).
 */
public interface RecommendedItemView {
    Long getIdContenido();
    String getTitulo();
    String getPosterKey();
    String getApiId();
    String getOrigen();
    String getTipoContenido();
    Double getScore();
}
