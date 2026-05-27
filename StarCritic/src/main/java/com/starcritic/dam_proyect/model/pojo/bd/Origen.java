package com.starcritic.dam_proyect.model.pojo.bd;

/**
 * Origen de un contenido del catálogo: OMDb (películas/series), RAWG
 * (videojuegos) o LOCAL (alta manual desde la propia aplicación).
 *
 * @author Jesús Santos Baquero
 */
public enum Origen {
    OMDB, RAWG, LOCAL;

    public boolean esLocal() {
        return this == LOCAL;
    }

    public boolean esExterno() {
        return this != LOCAL;
    }
}
