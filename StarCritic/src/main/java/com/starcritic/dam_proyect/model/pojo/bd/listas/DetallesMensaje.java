package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.Mensaje;
import java.util.List;

/**
 * Envoltorio de una lista de {@link Mensaje} devuelta por la API.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesMensaje {

    private List<Mensaje> mensajes;

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    @Override
    public String toString() {
        return "DetallesMensaje{" +
                "mensajes=" + mensajes +
                '}';
    }
}
