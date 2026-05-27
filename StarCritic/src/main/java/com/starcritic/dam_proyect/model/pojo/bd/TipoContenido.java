package com.starcritic.dam_proyect.model.pojo.bd;

/**
 * Tipo de un contenido del catálogo: película, serie o videojuego.
 *
 * @author Jesús Santos Baquero
 */
public enum TipoContenido {
    PELICULA, SERIE, VIDEOJUEGO;

    public boolean esAudiovisual() {
        return this != VIDEOJUEGO;
    }

}
