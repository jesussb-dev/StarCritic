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

    /**
     * Registrar una visita de un usuario a un contenido. El backend hace upsert:
     * inserta la fila si no existe o incrementa el contador si ya existia.
     * @param contenidoUsuario la relación contenido-usuario a registrar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean crearContenidoUsuario(ContenidoUsuario contenidoUsuario) {
        LocalDate fecha = contenidoUsuario.getFechaVisita();
        String path = "/contenido-usuario/" + contenidoUsuario.getIdUsuarioRegistrado()
                + "/" + contenidoUsuario.getIdContenido() + "/visita"
                + (fecha != null ? "?fecha=" + fecha : "");
        return ApiClient.get().postOk(path, null);
    }
}
