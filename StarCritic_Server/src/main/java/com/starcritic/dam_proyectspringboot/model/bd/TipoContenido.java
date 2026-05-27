package com.starcritic.dam_proyectspringboot.model.bd;

/**
 * @author Jesús Santos Baquero
 */
public enum TipoContenido {
    PELICULA, SERIE, VIDEOJUEGO;

    public boolean esAudiovisual() {
        return this != VIDEOJUEGO;
    }

}
