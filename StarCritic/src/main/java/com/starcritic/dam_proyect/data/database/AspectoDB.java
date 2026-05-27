package com.starcritic.dam_proyect.data.database;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.Aspecto;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesAspecto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Aspectos de valoración, vía API REST.
 *
 * @author Jesús Santos Baquero
 */
public class AspectoDB {

    /**
     * Obtener todos los aspectos existentes en la base de datos del servidor.
     * @return todos los aspectos en formato lista.
     */
    public static List<Aspecto> obtenerTodosLosAspectos() {
        return lista("/aspectos");
    }

    /**
     * Obtener todos los aspectos que pueda tener un contenido audiovisual.
     * @return los aspectos de audiovisual en formato lista.
     */
    public static List<Aspecto> obtenerAspectosAudiovisual() {
        return lista("/aspectos/audiovisual");
    }

    /**
     * Obtener todos los aspectos que pueda tener un videojuego.
     * @return los aspectos de videojuego en formato lista.
     */
    public static List<Aspecto> obtenerAspectosVideojuego() {
        return lista("/aspectos/videojuego");
    }

    /**
     * Obtener un aspecto a traves de su identificador en la base de datos.
     * @param idAspecto el identificador unico del aspecto.
     * @return el aspecto si existe, en caso contrario null.
     */
    public static Aspecto obtenerAspecto(int idAspecto) {
        return ApiClient.get().getObject("/aspectos/" + idAspecto, Aspecto.class);
    }

    private static List<Aspecto> lista(String path) {
        Aspecto[] respuesta = ApiClient.get().getObject(path, Aspecto[].class);
        DetallesAspecto detalles = new DetallesAspecto();
        if (respuesta != null) {
            detalles.setAspectos(new ArrayList<>(Arrays.asList(respuesta)));
        } else {
            detalles.setAspectos(new ArrayList<>());
        }
        return detalles.getAspectos();
    }
}
