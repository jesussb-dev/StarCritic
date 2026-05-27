package com.starcritic.dam_proyectspringboot.model.bd;

public enum TipoContenido {
    PELICULA, SERIE, VIDEOJUEGO;

    public boolean esAudiovisual() {
        return this != VIDEOJUEGO;
    }

}
