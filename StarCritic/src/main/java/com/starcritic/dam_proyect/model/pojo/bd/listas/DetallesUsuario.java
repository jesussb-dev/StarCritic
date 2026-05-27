package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.UsuarioRegistrado;
import java.util.List;

/**
 * Envoltorio de una lista de {@link UsuarioRegistrado} devuelta por la API.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesUsuario {

    private List<UsuarioRegistrado> usuarios;

    public void setUsuarios(List<UsuarioRegistrado> usuarios) {
        this.usuarios = usuarios;
    }

    public List<UsuarioRegistrado> getUsuarios() {
        return usuarios;
    }

    @Override
    public String toString() {
        return "DetallesUsuario{" +
                "usuarios=" + usuarios +
                '}';
    }
}
