package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.ContenidoAudiovisual;
import java.util.List;

/**
 * Envoltorio de una lista de {@link ContenidoAudiovisual} devuelta por la API.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesContenidoAudiovisual {

    private List<ContenidoAudiovisual> contenidosAudiovisuales;

    public void setContenidosAudiovisuales(List<ContenidoAudiovisual> contenidosAudiovisuales) {
        this.contenidosAudiovisuales = contenidosAudiovisuales;
    }

    public List<ContenidoAudiovisual> getContenidosAudiovisuales() {
        return contenidosAudiovisuales;
    }

    @Override
    public String toString() {
        return "DetallesContenidoAudiovisual{" +
                "contenidosAudiovisuales=" + contenidosAudiovisuales +
                '}';
    }
}
