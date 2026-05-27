package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.RecommendedItem;
import java.util.List;

/**
 * Envoltorio de una lista de {@link RecommendedItem} devuelta por la API de
 * recomendaciones.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesRecommendedItem {

    private List<RecommendedItem> contenidos;

    public void setContenidos(List<RecommendedItem> contenidos) {
        this.contenidos = contenidos;
    }

    public List<RecommendedItem> getContenidos() {
        return contenidos;
    }

    @Override
    public String toString() {
        return "DetallesRecommendedItem{" +
                "contenidos=" + contenidos +
                '}';
    }
}
