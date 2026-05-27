package com.starcritic.dam_proyect.data.database;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Consultas agregadas para los paneles de estadísticas, vía API REST.
 *
 * Métodos {@code *Usuario} → datos PERSONALES; el resto, GENERALES.
 * Se devuelve un {@link LinkedHashMap} para conservar el orden que envía la API.
 *
 * @author Jesús Santos Baquero
 */
public class EstadisticasDB {

    // ===================== PERSONALES ===================== //

    public static Map<String, Double> contenidoEnListasPorTipo(int idUsuario) {
        return pares("/estadisticas/usuario/" + idUsuario + "/contenido-en-listas");
    }

    public static Map<String, Double> mediaPorAspectoUsuario(int idUsuario) {
        return pares("/estadisticas/usuario/" + idUsuario + "/media-por-aspecto");
    }

    public static Map<String, Double> visitasPorMesUsuario(int idUsuario) {
        return pares("/estadisticas/usuario/" + idUsuario + "/visitas-por-mes");
    }


    // ===================== GENERALES ===================== //

    public static Map<String, Double> topContenidoMasVisitado(int limite) {
        return pares("/estadisticas/top-visitado?limite=" + limite);
    }

    public static Map<String, Double> catalogoPorOrigen() {
        return pares("/estadisticas/catalogo-por-origen");
    }


    public static Map<String, Double> distribucionPuntuaciones() {
        return pares("/estadisticas/distribucion-puntuaciones");
    }


    // ===================== Helper ===================== //

    private static Map<String, Double> pares(String path) {
        LinkedHashMap<String, Double> datos = ApiClient.get().getObject(path, LinkedHashMap.class);
        if (datos == null) {
            return new LinkedHashMap<>();
        }
        return datos;
    }
}
