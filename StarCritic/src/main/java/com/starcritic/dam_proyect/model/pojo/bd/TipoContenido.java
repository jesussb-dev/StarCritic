package com.starcritic.dam_proyect.model.pojo.bd;

public enum TipoContenido {
    PELICULA, SERIE, VIDEOJUEGO;

    public boolean esAudiovisual() {
        return this != VIDEOJUEGO;
    }

}
