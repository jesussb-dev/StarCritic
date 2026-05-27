package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.ListaContenido;
import com.starcritic.dam_proyectspringboot.model.bd.ListaUsuarioId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Jesús Santos Baquero
 */
@Repository
public interface ListaContenidoRepository extends JpaRepository<ListaContenido, ListaUsuarioId> {
    /**
     * Comprueba si el contenido existe en una lista de un usuario
     * @param idUsuario el usuario al que le pertenece la lista
     * @param nombreLista la lista en la que se buscara el contenido
     * @param idContenido contenido a buscar
     * @return si existe el contenido en esa lista
     */
    @Query("SELECT CASE WHEN COUNT(lc) > 0 THEN TRUE ELSE FALSE END FROM ListaContenido lc "
            + "WHERE lc.id.idUsuarioRegistrado = :idUsuario "
            + "AND lc.id.nombreLista = :nombreLista "
            + "AND lc.id.idContenido = :idContenido")
    boolean contentExistInList(@Param("idUsuario") Long idUsuario,
                               @Param("nombreLista") String nombreLista,
                               @Param("idContenido") Long idContenido);

    /**
     * Se obtienen todos los contenidos de una lista de un usuario
     * @param idUsuario el usuario al que le pertenecera una lista
     * @param nombreLista el nombre de la lista
     * @return todos los contenidos que tenia esa lista
     */
    @Query("SELECT lc FROM ListaContenido lc "
            + "WHERE lc.id.idUsuarioRegistrado = :idUsuario "
            + "AND lc.id.nombreLista = :nombreLista")
    List<ListaContenido> getUserListContents(@Param("idUsuario") Long idUsuario,
                                             @Param("nombreLista") String nombreLista);

    /**
     * Eliminar in contenido de una lista
     * @param idUsuario el usuario al que le pertenecera una lista 
     * @param nombreLista  la lista sobre la que queremos actuar
     * @param idContenido el contenido a eliminar
     * @return si la operación fue exitosa
     */
    @Modifying
    @Query("DELETE FROM ListaContenido lc "
            + "WHERE lc.usuario.idUsuario = :idUsuario AND lc.id.nombreLista = :nombreLista "
            + "AND lc.contenido.idContenido = :idContenido")
    int eliminarContenidoDeLista(@Param("idUsuario") Long idUsuario, @Param("nombreLista") String nombreLista,@Param("idContenido") Long idContenido);
}
