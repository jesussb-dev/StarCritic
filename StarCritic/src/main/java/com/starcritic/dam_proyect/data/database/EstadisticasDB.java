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

    /**
     * Obtener el contenido en listas de un usuario agrupado por tipo.
     * @param idUsuario el identificador del usuario.
     * @return un mapa con el tipo de contenido y la cantidad acumulada.
     */
    public static Map<String, Double> contenidoEnListasPorTipo(int idUsuario) {
        return pares("/estadisticas/usuario/" + idUsuario + "/contenido-en-listas");
    }

    /**
     * Obtener la media de puntuaciones por aspecto de un usuario.
     * @param idUsuario el identificador del usuario.
     * @return un mapa con el nombre del aspecto y su media de puntuaciones.
     */
    public static Map<String, Double> mediaPorAspectoUsuario(int idUsuario) {
        return pares("/estadisticas/usuario/" + idUsuario + "/media-por-aspecto");
    }

    /**
     * Obtener las visitas a contenido de un usuario agrupadas por mes.
     * @param idUsuario el identificador del usuario.
     * @return un mapa con el mes y el número de visitas en ese mes.
     */
    public static Map<String, Double> visitasPorMesUsuario(int idUsuario) {
        return pares("/estadisticas/usuario/" + idUsuario + "/visitas-por-mes");
    }


    // ===================== GENERALES ===================== //

    /**
     * Obtener el top de contenidos mas visitados del catálogo.
     * @param limite el número máximo de contenidos a devolver.
     * @return un mapa con el título del contenido y el número de visitas.
     */
    public static Map<String, Double> topContenidoMasVisitado(int limite) {
        return pares("/estadisticas/top-visitado?limite=" + limite);
    }

    /**
     * Obtener la distribución del catálogo según el origen del contenido.
     * @return un mapa con el nombre del origen y el número de contenidos.
     */
    public static Map<String, Double> catalogoPorOrigen() {
        return pares("/estadisticas/catalogo-por-origen");
    }


    /**
     * Obtener la distribución global de puntuaciones del catálogo.
     * @return un mapa con la puntuación y el número de criticas con esa puntuación.
     */
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
