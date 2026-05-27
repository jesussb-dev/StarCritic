package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.ListaUsuario;
import java.util.List;

/**
 * Envoltorio de una lista de {@link ListaUsuario} devuelta por la API.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesListaUsuario {

    private List<ListaUsuario> listas;

    public void setListas(List<ListaUsuario> listas) {
        this.listas = listas;
    }

    public List<ListaUsuario> getListas() {
        return listas;
    }

    @Override
    public String toString() {
        return "DetallesListaUsuario{" +
                "listas=" + listas +
                '}';
    }
}
