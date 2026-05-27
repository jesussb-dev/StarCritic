package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.Aspecto;
import java.util.List;

/**
 * Envoltorio de una lista de {@link Aspecto} devuelta por la API.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesAspecto {

    private List<Aspecto> aspectos;

    public void setAspectos(List<Aspecto> aspectos) {
        this.aspectos = aspectos;
    }

    public List<Aspecto> getAspectos() {
        return aspectos;
    }

    @Override
    public String toString() {
        return "DetallesAspecto{" +
                "aspectos=" + aspectos +
                '}';
    }
}
