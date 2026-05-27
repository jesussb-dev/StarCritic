package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.Contenido;
import com.starcritic.dam_proyectspringboot.repository.projection.KeyValueView;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Consultas agregadas para los paneles de estadisticas.
 *
 * Los metodos {@code *Raw} devuelven la proyeccion {@link KeyValueView}; los
 * metodos por defecto las envuelven en un {@link LinkedHashMap} para conservar
 * el orden definido por SQL (igual que la antigua EstadisticasDB).
 */
public interface EstadisticasRepository extends Repository<Contenido, Long> {

    // ===================== PERSONALES ===================== //
    /**
     * Este metodo devolvera la cantidad de contenidos que se encuentran en las
     * listas de un usuario diferenciandolos por su tipo
     * @param idUsuario el usuario del cual se obtienen los datos
     * @return una lista de elementos clave valor (TIpoContenido, nº contenidos) 
     */
    @Query(value = "SELECT c.tipo_contenido AS k, COUNT(DISTINCT c.ID_contenido) AS v "
            + "FROM lista_contenido lc "
            + "JOIN contenido c ON c.ID_contenido = lc.ID_contenido "
            + "WHERE lc.ID_usuario_registrado = :idUsuario "
            + "GROUP BY c.tipo_contenido", nativeQuery = true)
    List<KeyValueView> contenidoEnListasPorTipoRaw(@Param("idUsuario") Long idUsuario);
    
    /**
     * En este método se obtendran todas las medias de todas las criticas hechas por
     * el usuario en cada aspecto.
     * @param idUsuario el usuario del cual se obtienen los datos
     * @return una lsita de elementos clave valor (Aspecto, media)                                                                                                                              
     */
    @Query(value = "SELECT a.nombre AS k, AVG(cr.puntuacion) AS v "
            + "FROM critica cr "
            + "JOIN ( SELECT ID_critica_audiovisual AS id_c, ID_aspecto FROM critica_audiovisual "
            + "       UNION ALL "
            + "       SELECT ID_critica_videojuego, ID_aspecto FROM critica_videojuego ) ca "
            + "  ON ca.id_c = cr.ID_critica "
            + "JOIN aspecto a ON a.ID_aspecto = ca.ID_aspecto "
            + "WHERE cr.ID_usuario_registrado = :idUsuario "
            + "GROUP BY a.nombre ORDER BY v DESC", nativeQuery = true)
    List<KeyValueView> mediaPorAspectoUsuarioRaw(@Param("idUsuario") Long idUsuario);

    /**
     * Obtiene la cantidad de visitas por mes que realiza un usuario
     * @param idUsuario el usuario del cual se obtienen los datos
     * @return  una lista de elementos clave valor (Mes, nº visitas)
     */
    @Query(value = "SELECT DATE_FORMAT(fecha_visita,'%Y-%m') AS k, SUM(num_visitas) AS v "
            + "FROM contenido_usuario "
            + "WHERE ID_usuario_registrado = :idUsuario "
            + "GROUP BY k ORDER BY k", nativeQuery = true)
    List<KeyValueView> visitasPorMesUsuarioRaw(@Param("idUsuario") Long idUsuario);



    // ===================== GENERALES ===================== //
    /**
     * Metodo que devuelve los contenidos más visitados de la aplicación junto al
     * nº de visitas
     * @param limite el nº máximo de contenidos que devolvera el método
     * @return una lista de elementos clave valor (Contenido, nº visitas)
     */
    @Query(value = "SELECT c.titulo AS k, SUM(cu.num_visitas) AS v "
            + "FROM contenido c "
            + "JOIN contenido_usuario cu ON cu.ID_contenido = c.ID_contenido "
            + "GROUP BY c.ID_contenido, c.titulo "
            + "ORDER BY v DESC LIMIT :limite", nativeQuery = true)
    List<KeyValueView> topContenidoMasVisitadoRaw(@Param("limite") int limite);

    /**
     * Devuele la cantidad de cotnenidos guardados en la BD diferenciandolos por su origen
     * @return una lista de elementos clave valor (Origen, nº contenidos)
     */
    @Query(value = "SELECT origen AS k, COUNT(*) AS v FROM contenido GROUP BY origen", nativeQuery = true)
    List<KeyValueView> catalogoPorOrigenRaw();

 
    /**
     * Devuelve todas las medias generales de todos los contenidos divididas en cinco grupos
     * por su puntaje.
     * @return una lista de elementos clave valor (Grupo, nº contenidos)
     */
    @Query(value = "SELECT CASE "
            + "  WHEN puntuacion < 20 THEN '0-19' "
            + "  WHEN puntuacion < 40 THEN '20-39' "
            + "  WHEN puntuacion < 60 THEN '40-59' "
            + "  WHEN puntuacion < 80 THEN '60-79' "
            + "  ELSE '80-100' END AS k, "
            + "COUNT(*) AS v "
            + "FROM critica GROUP BY k ORDER BY MIN(puntuacion)", nativeQuery = true)
    List<KeyValueView> distribucionPuntuacionesRaw();



    // ===================== Envoltorios Map ===================== //
    /**
     * Todos estos metodos sirven para obtener desde clases que implementen a esta
     * interfaz los datos mapeados de los metodos anteriores, haciendolos adecuados
     * para pasarlos a Json
     */
    default Map<String, Double> contenidoEnListasPorTipo(Long idUsuario) {
        return aMapa(contenidoEnListasPorTipoRaw(idUsuario));
    }

    default Map<String, Double> mediaPorAspectoUsuario(Long idUsuario) {
        return aMapa(mediaPorAspectoUsuarioRaw(idUsuario));
    }

    default Map<String, Double> visitasPorMesUsuario(Long idUsuario) {
        return aMapa(visitasPorMesUsuarioRaw(idUsuario));
    }


    default Map<String, Double> topContenidoMasVisitado(int limite) {
        return aMapa(topContenidoMasVisitadoRaw(limite));
    }

    default Map<String, Double> catalogoPorOrigen() {
        return aMapa(catalogoPorOrigenRaw());
    }

    default Map<String, Double> distribucionPuntuaciones() {
        return aMapa(distribucionPuntuacionesRaw());
    }

    
    /**
     * Pasar los objetos clave valor a un Mapa 
     * @param filas lista de objetos clave valor
     * @return un mapa de la lista de objetos
     */
    private static Map<String, Double> aMapa(List<KeyValueView> filas) {
        Map<String, Double> datos = new LinkedHashMap<>();
        for (KeyValueView fila : filas) {
            datos.put(fila.getK(), fila.getV());
        }
        return datos;
    }
}
