package com.starcritic.dam_proyect.data.database;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.ContenidoUsuario;
import java.time.LocalDate;

/**
 * Relación contenido-usuario (visitas), vía API REST. El registro de visita es
 * un upsert en el backend (inserta o incrementa el contador).
 *
 * @author Jesús Santos Baquero
 */
public class ContenidoUsuarioDB {

    public static boolean crearContenidoUsuario(ContenidoUsuario contenidoUsuario) {
        LocalDate fecha = contenidoUsuario.getFechaVisita();
        String path = "/contenido-usuario/" + contenidoUsuario.getIdUsuarioRegistrado()
                + "/" + contenidoUsuario.getIdContenido() + "/visita"
                + (fecha != null ? "?fecha=" + fecha : "");
        return ApiClient.get().postOk(path, null);
    }
}
