package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.Videojuego;
import java.util.List;

/**
 * Envoltorio de una lista de {@link Videojuego} devuelta por la API.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesVideojuego {

    private List<Videojuego> videojuegos;

    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    @Override
    public String toString() {
        return "DetallesVideojuego{" +
                "videojuegos=" + videojuegos +
                '}';
    }
}
