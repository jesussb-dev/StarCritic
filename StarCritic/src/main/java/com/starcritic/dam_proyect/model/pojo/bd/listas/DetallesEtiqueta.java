package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.EtiquetaEditorial;
import java.util.List;

/**
 * Envoltorio de una lista de {@link EtiquetaEditorial} devuelta por la API.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesEtiqueta {

    private List<EtiquetaEditorial> etiquetas;

    public void setEtiquetas(List<EtiquetaEditorial> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public List<EtiquetaEditorial> getEtiquetas() {
        return etiquetas;
    }

    @Override
    public String toString() {
        return "DetallesEtiqueta{" +
                "etiquetas=" + etiquetas +
                '}';
    }
}
