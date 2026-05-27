package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Jesús Santos Baquero
 */
@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

    /**
     * Media de puntuaciones de un aspecto sobre un contenido audiovisual. 
     * @param idContenido contenido sobre el que se quiere averiguariar su media en algun aspecto.
     * @param idAspecto aspecto sobre el que se quiso calcular la media.
     * @return dovolvera la media del aspecto seleccionado de ese contenido especifico
     */
    @Query("SELECT COALESCE(AVG(c.puntuacion), 0) FROM CriticaAudiovisual c "
            + "WHERE c.contenidoAudiovisual.idContenido = :idContenido AND c.aspecto.idAspecto = :idAspecto")
    double mediaAspectoAudiovisual(@Param("idContenido") Long idContenido,@Param("idAspecto") Long idAspecto);

    /** 
     * Media de puntuaciones de un aspecto sobre un contenido videojuego. 
     * @param idVideojuego contenido sobre el que se quiere averiguariar su media en algun aspecto.
     * @param idAspecto aspecto sobre el que se quiso calcular la media.
     * @return dovolvera la media del aspecto seleccionado de ese contenido especifico  
     */
    @Query("SELECT COALESCE(AVG(c.puntuacion), 0) FROM CriticaVideojuego c "
            + "WHERE c.videojuego.idContenido = :idVideojuego AND c.aspecto.idAspecto = :idAspecto")
    double mediaAspectoVideojuego(@Param("idVideojuego") Long idVideojuego,@Param("idAspecto") Long idAspecto);
    /**
     * Metodo para modificar si un contenido se encuentra oculto o no
     * @param idContenido el contenido sobre el que se quiere hacer la modificación
     * @param oculto el nuevo valor tras la modificación
     * @return devolveara si el metodo ha funcionado
     */
    @Modifying
    @Query("UPDATE Contenido c SET c.oculto = :oculto WHERE c.idContenido = :idContenido")
    int actualizarOculto(@Param("idContenido") Long idContenido, @Param("oculto") boolean oculto);
    
    /**
     * Metodo para modificar si un contenido se encuentra destacado o no
     * @param idContenido el contenido sobre el que se quiere hacer la modificaciónz
     * @param destacado el nuevo valor tras la modificación
     * @return devolveara si el metodo ha funcionado
     */
    @Modifying
    @Query("UPDATE Contenido c SET c.destacado = :destacado WHERE c.idContenido = :idContenido")
    int actualizarDestacado(@Param("idContenido") Long idContenido, @Param("destacado") boolean destacado);
}
