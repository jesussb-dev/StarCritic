package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.Contenido;
import com.starcritic.dam_proyectspringboot.model.bd.TipoContenido;
import com.starcritic.dam_proyectspringboot.repository.projection.RecommendedItemView;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Algoritmo de recomendaciones por comportamiento (migrado de RecomendacionDB).
 *
 * Senales: visitas (×1), resenas ponderadas (SUM(puntuacion)/33.3), entradas en
 * listas (×2) y flag destacado (+5). La consulta personalizada anade bonus
 * colaborativo, bonus de afinidad por aspectos y penalizaciones por contenido
 * ya resenado o marcado como visto.
 */
public interface RecomendacionRepository extends Repository<Contenido, Long> {
    /**
     * Devolvera en base al nº de visitas, a la puntuacion general y las veces que ha sido guardado en lista
     * una lista de contenido
     * @param tipo el tipo de contenido del que seran los contenidos de las listas
     * @param limite el limite de objetos que obtendra
     * @return la lista de contenidos resultado de este "algoritmo" (consulta)
     */
    @Query(value =
        "SELECT "
      + "  c.ID_contenido AS idContenido, c.titulo AS titulo, c.poster_key AS posterKey, "
      + "  COALESCE(ca.ID_Api, CAST(cv.ID_Api AS CHAR)) AS apiId, "
      + "  c.origen AS origen, c.tipo_contenido AS tipoContenido, "
      + "  ( "
      + "    COALESCE((SELECT SUM(cu.num_visitas) FROM contenido_usuario cu "
      + "              WHERE cu.ID_contenido = c.ID_contenido), 0) "
      + "  + COALESCE((SELECT SUM(cr.puntuacion) FROM critica cr "
      + "              JOIN critica_audiovisual cav2 ON cav2.ID_critica_audiovisual = cr.ID_critica "
      + "              WHERE cav2.ID_contenido_audiovisual = c.ID_contenido), 0) / 33.3 "
      + "  + COALESCE((SELECT SUM(cr.puntuacion) FROM critica cr "
      + "              JOIN critica_videojuego  cvg2 ON cvg2.ID_critica_videojuego = cr.ID_critica "
      + "              WHERE cvg2.ID_videojuego = c.ID_contenido), 0) / 33.3 "
      + "  + COALESCE((SELECT COUNT(DISTINCT CONCAT(lc.ID_usuario_registrado,'_',lc.nombre_lista)) "
      + "              FROM lista_contenido lc WHERE lc.ID_contenido = c.ID_contenido), 0) * 2 "
      + "  + IF(c.destacado, 5, 0) "
      + "  ) AS score "
      + "FROM contenido c "
      + "LEFT JOIN contenido_audiovisual ca ON ca.ID_contenido_audiovisual = c.ID_contenido "
      + "LEFT JOIN videojuego            cv ON cv.ID_videojuego             = c.ID_contenido "
      + "WHERE c.oculto = FALSE AND c.tipo_contenido = :tipo "
      + "ORDER BY score DESC "
      + "LIMIT :limite", nativeQuery = true)
    List<RecommendedItemView> obtenerRecomendacionesGlobal(@Param("tipo") String tipo,@Param("limite") int limite);
    
    /**
     * Devolvera en base al nº de visitas, a la puntuacion general y las veces que ha sido guardado en lista
     * una lista de contenido y las preferencias del usuario una lista de contenidos
     * @param idUsuario el usuario sobre el que utilizar el algoritmo
     * @param tipo el tipo de contenido del que seran los contenidos de las listas
     * @param limite el limite de objetos que obtendra
     * @return la lista de contenidos resultados del algoritmo
     */
    @Query(value =
        "SELECT "
      + "  c.ID_contenido AS idContenido, c.titulo AS titulo, c.poster_key AS posterKey, "
      + "  COALESCE(ca.ID_Api, CAST(cv.ID_Api AS CHAR)) AS apiId, "
      + "  c.origen AS origen, c.tipo_contenido AS tipoContenido, "
      + "  ( "
      + "    COALESCE((SELECT SUM(cu.num_visitas) FROM contenido_usuario cu "
      + "              WHERE cu.ID_contenido = c.ID_contenido), 0) "
      + "  + COALESCE((SELECT SUM(cr.puntuacion) FROM critica cr "
      + "              JOIN critica_audiovisual cav2 ON cav2.ID_critica_audiovisual = cr.ID_critica "
      + "              WHERE cav2.ID_contenido_audiovisual = c.ID_contenido), 0) / 33.3 "
      + "  + COALESCE((SELECT SUM(cr.puntuacion) FROM critica cr "
      + "              JOIN critica_videojuego  cvg2 ON cvg2.ID_critica_videojuego = cr.ID_critica "
      + "              WHERE cvg2.ID_videojuego = c.ID_contenido), 0) / 33.3 "
      + "  + COALESCE((SELECT COUNT(DISTINCT CONCAT(lc.ID_usuario_registrado,'_',lc.nombre_lista)) "
      + "              FROM lista_contenido lc WHERE lc.ID_contenido = c.ID_contenido), 0) * 2 "
      + "  + IF(c.destacado, 5, 0) "
      + "  + ( "
      + "      SELECT COUNT(DISTINCT peer.ID_usuario_registrado) * 10 "
      + "      FROM contenido_usuario peer "
      + "      WHERE peer.ID_usuario_registrado != :idUsuario "
      + "      AND peer.ID_contenido IN ( "
      + "          SELECT ID_contenido FROM contenido_usuario WHERE ID_usuario_registrado = :idUsuario "
      + "          UNION "
      + "          SELECT ID_contenido FROM lista_contenido   WHERE ID_usuario_registrado = :idUsuario "
      + "      ) "
      + "      AND peer.ID_usuario_registrado IN ( "
      + "          SELECT ID_usuario_registrado FROM contenido_usuario WHERE ID_contenido = c.ID_contenido "
      + "          UNION "
      + "          SELECT ID_usuario_registrado FROM lista_contenido   WHERE ID_contenido = c.ID_contenido "
      + "      ) "
      + "  ) "
      + "  + CASE "
      + "      WHEN c.tipo_contenido IN ('PELICULA', 'SERIE') THEN "
      + "        COALESCE(( "
      + "          SELECT AVG(cr_o.puntuacion) "
      + "          FROM critica cr_o "
      + "          JOIN critica_audiovisual cav_o ON cav_o.ID_critica_audiovisual = cr_o.ID_critica "
      + "          WHERE cav_o.ID_contenido_audiovisual = c.ID_contenido "
      + "            AND cr_o.ID_usuario_registrado != :idUsuario "
      + "            AND cav_o.ID_aspecto IN ( "
      + "                SELECT cav_m.ID_aspecto "
      + "                FROM critica cr_m "
      + "                JOIN critica_audiovisual cav_m ON cav_m.ID_critica_audiovisual = cr_m.ID_critica "
      + "                WHERE cr_m.ID_usuario_registrado = :idUsuario "
      + "                GROUP BY cav_m.ID_aspecto "
      + "                HAVING AVG(cr_m.puntuacion) >= 70 "
      + "            ) "
      + "        ), 0) / 2.0 "
      + "      ELSE "
      + "        COALESCE(( "
      + "          SELECT AVG(cr_o.puntuacion) "
      + "          FROM critica cr_o "
      + "          JOIN critica_videojuego cvg_o ON cvg_o.ID_critica_videojuego = cr_o.ID_critica "
      + "          WHERE cvg_o.ID_videojuego = c.ID_contenido "
      + "            AND cr_o.ID_usuario_registrado != :idUsuario "
      + "            AND cvg_o.ID_aspecto IN ( "
      + "                SELECT cvg_m.ID_aspecto "
      + "                FROM critica cr_m "
      + "                JOIN critica_videojuego cvg_m ON cvg_m.ID_critica_videojuego = cr_m.ID_critica "
      + "                WHERE cr_m.ID_usuario_registrado = :idUsuario "
      + "                GROUP BY cvg_m.ID_aspecto "
      + "                HAVING AVG(cr_m.puntuacion) >= 70 "
      + "            ) "
      + "        ), 0) / 2.0 "
      + "    END "
      + "  - COALESCE(( "
      + "      SELECT 100 FROM critica cr "
      + "      JOIN critica_audiovisual cav ON cav.ID_critica_audiovisual = cr.ID_critica "
      + "      WHERE cr.ID_usuario_registrado = :idUsuario "
      + "        AND cav.ID_contenido_audiovisual = c.ID_contenido LIMIT 1 "
      + "  ), 0) "
      + "  - COALESCE(( "
      + "      SELECT 100 FROM critica cr "
      + "      JOIN critica_videojuego cvg ON cvg.ID_critica_videojuego = cr.ID_critica "
      + "      WHERE cr.ID_usuario_registrado = :idUsuario "
      + "        AND cvg.ID_videojuego = c.ID_contenido LIMIT 1 "
      + "  ), 0) "
      + "  - COALESCE(( "
      + "      SELECT 50 FROM lista_contenido lc "
      + "      WHERE lc.ID_usuario_registrado = :idUsuario "
      + "        AND lc.nombre_lista = 'Vistos/Jugados' "
      + "        AND lc.ID_contenido = c.ID_contenido LIMIT 1 "
      + "  ), 0) "
      + "  ) AS score "
      + "FROM contenido c "
      + "LEFT JOIN contenido_audiovisual ca ON ca.ID_contenido_audiovisual = c.ID_contenido "
      + "LEFT JOIN videojuego            cv ON cv.ID_videojuego             = c.ID_contenido "
      + "WHERE c.oculto = FALSE AND c.tipo_contenido = :tipo "
      + "ORDER BY score DESC "
      + "LIMIT :limite", nativeQuery = true)
    List<RecommendedItemView> obtenerRecomendacionesPersonalizado(@Param("idUsuario") Long idUsuario,@Param("tipo") String tipo,@Param("limite") int limite);

    /**
     * Devolvera dependiendo de los datos proporcionador una lista de contenidos
     * @param idUsuario el usuario sobre el que utilizar el algoritmo
     * @param tipo el tipo de contenido del que seran los contenidos de las listas
     * @param limite el limite de objetos que obtendra
     * @return 
     */
    default List<RecommendedItemView> obtenerRecomendaciones(TipoContenido tipo, Long idUsuario, int limite) {
        if (idUsuario == null || idUsuario < 0) {
            return obtenerRecomendacionesGlobal(tipo.name(), limite);
        }
        return obtenerRecomendacionesPersonalizado(idUsuario, tipo.name(), limite);
    }
}
