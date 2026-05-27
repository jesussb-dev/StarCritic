package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.ListaUsuario;
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
public interface ListaUsuarioRepository extends JpaRepository<ListaUsuario, ListaUsuario.PK> {

    /**
     * Se obtendran todas las listas que le pertenecen a un usuario
     * @param idUsuarioRegistrado el usuario del que queremos obtener la lista
     * @return todas las listas que le pertenezcan al usuario
     */
    @Query("SELECT lu FROM ListaUsuario lu WHERE lu.idUsuarioRegistrado = :idUsuarioRegistrado")
    List<ListaUsuario> getListasDeUsuario(@Param("idUsuarioRegistrado") Long idUsuarioRegistrado);

    /**
     * Eliminar una lista
     * @param idUsuarioRegistrado el usuario al que le pertenece la lsita
     * @param nombreLista  la lista a elminar
     */
    @Modifying
    @Query("DELETE FROM ListaUsuario lu "
            + "WHERE lu.idUsuarioRegistrado = :idUsuarioRegistrado "
            + "AND lu.nombreLista = :nombreLista")
    void deleteLista(@Param("idUsuarioRegistrado") Long idUsuarioRegistrado,
                     @Param("nombreLista") String nombreLista);
}
