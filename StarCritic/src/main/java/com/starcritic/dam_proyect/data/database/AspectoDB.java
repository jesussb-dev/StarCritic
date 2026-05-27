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

    public static List<Aspecto> obtenerTodosLosAspectos() {
        return lista("/aspectos");
    }

    public static List<Aspecto> obtenerAspectosAudiovisual() {
        return lista("/aspectos/audiovisual");
    }

    public static List<Aspecto> obtenerAspectosVideojuego() {
        return lista("/aspectos/videojuego");
    }

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
