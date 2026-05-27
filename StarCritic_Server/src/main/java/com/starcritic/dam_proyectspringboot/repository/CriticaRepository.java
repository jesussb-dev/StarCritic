package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.Critica;
import com.starcritic.dam_proyectspringboot.model.bd.CriticaAudiovisual;
import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Jesús Santos Baquero
 */
@Repository
public interface CriticaRepository extends JpaRepository<Critica, Long> {
    /**
     * Metodo para obtener la lista de criticas que se han hecho a un aspecto de 
     * un contenido audiovisual.
     * @param idAspecto el aspecto del cual se desean sacar sus criticas
     * @param idContenido el contenido audiovidual al cual esta asignado ese aspecto
     * @return  todas las críticas a ese aspecto de ese contenido
     */
    @Query("SELECT c FROM CriticaAudiovisual c "
            + "WHERE c.aspecto.idAspecto = :idAspecto AND c.contenidoAudiovisual.idContenido = :idContenido "
            + "ORDER BY c.puntuacion DESC")
    List<CriticaAudiovisual> obtenerCriticasAudiovisualPorAspecto(@Param("idAspecto") Long idAspecto,@Param("idContenido") Long idContenido);
    
    /**
     * Metodo para obtener la lista de criticas que se han hecho a un aspecto de 
     * un contenido videojuego. 
     * @param idAspecto el aspecto del cual se desean sacar sus criticas
     * @param idVideojuego el contenido videojuego al cual esta asignado ese aspecto
     * @return todas las críticas a ese aspecto de ese contenido
     */
    @Query("SELECT c FROM CriticaVideojuego c "
            + "WHERE c.aspecto.idAspecto = :idAspecto AND c.videojuego.idContenido = :idVideojuego "
            + "ORDER BY c.puntuacion DESC")
    List<CriticaVideojuego> obtenerCriticasVideojuegoPorAspecto(@Param("idAspecto") Long idAspecto,@Param("idVideojuego") Long idVideojuego);
    
    /**
     * Metodo para obtener todas las críticas que ha hecho un usuario a un aspecto
     * de cualquier contenido audiovisual
     * @param idAspecto el aspecto del cual se desean sacar sus criticas
     * @param idUsuario el usuario de todas las críticas de la lista
     * @return todas las criticas hechas por idUsuario a un aspecto de un contenido audiovisual
     */
    @Query("SELECT c FROM CriticaAudiovisual c "
            + "WHERE c.aspecto.idAspecto = :idAspecto AND c.usuarioRegistrado.idUsuario = :idUsuario "
            + "ORDER BY c.puntuacion DESC")
    List<CriticaAudiovisual> obtenerCriticasAudiovisualPorUsuario(@Param("idAspecto") Long idAspecto,@Param("idUsuario") Long idUsuario);
    
    /**
     * Metodo para obtener todas las críticas que ha hecho un usuario a un aspecto
     * de cualquier contenido videojuego
     * @param idAspecto el aspecto del cual se desean sacar sus criticas
     * @param idUsuario el usuario de todas las críticas de la lista
     * @return todas las criticas hechas por idUsuario a un aspecto de un contenido videojuego
     */
    @Query("SELECT c FROM CriticaVideojuego c "
            + "WHERE c.aspecto.idAspecto = :idAspecto AND c.usuarioRegistrado.idUsuario = :idUsuario "
            + "ORDER BY c.puntuacion DESC")
    List<CriticaVideojuego> obtenerCriticasVideojuegoPorUsuario(@Param("idAspecto") Long idAspecto,@Param("idUsuario") Long idUsuario);
    
    /**
     * Comprobar que una critica le pertenece a ese usuario o no.
     * @param idCritica critica la cual se busca saber su usuario
     * @param idUsuario el usuario que se quiere comprobar
     * @return si el usuario no hizo esa critica o no
     */
    boolean existsByIdCriticaAndUsuarioRegistrado_IdUsuario(Long idCritica, Long idUsuario);
    
    /**
     * Comprobar que una critica le pertenece a ese usuario o no.
     * @param idCritica critica la cual se busca saber su usuario
     * @param idUsuario el usuario que se quiere comprobar
     * @return si el usuario no hizo esa critica o no
     */
    default boolean esCriticaUsuario(Long idUsuario, Long idCritica) {
        return existsByIdCriticaAndUsuarioRegistrado_IdUsuario(idCritica, idUsuario);
    }
}
