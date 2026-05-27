package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.EtiquetaEditorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EtiquetaEditorialRepository extends JpaRepository<EtiquetaEditorial, Long> {
    /**
     * Obtener todas las etiquetas
     * @return  todas las etiquetas en formato lista
     */
    List<EtiquetaEditorial> findAllByOrderByNombreAsc();

    /**
     * Etiquetas asignadas a un contenido (relacion N:M contenido_etiqueta).
     * @param idContenido el contenido del cual se sacan las etiquetas.
     * @return las etiquetas de ese contenido en formato lista.
     */
    @Query(value = "SELECT e.* FROM etiqueta_editorial e "
            + "JOIN contenido_etiqueta ce ON ce.ID_etiqueta = e.ID_etiqueta "
            + "WHERE ce.ID_contenido = :idContenido "
            + "ORDER BY e.nombre", nativeQuery = true)
    List<EtiquetaEditorial> obtenerEtiquetasDe(@Param("idContenido") Long idContenido);
    
    /**
     * Metodo que consiste en asignar una etiqueta a un contenido registrado en la DB
     * @param idContenido contenido al que asignarle la etiqueta
     * @param idEtiqueta etiqueta que se le desea asignar al contenido
     * @return si la operación fue exitosa
     */
    @Modifying
    @Query(value = "INSERT IGNORE INTO contenido_etiqueta (ID_contenido, ID_etiqueta) VALUES (:idContenido, :idEtiqueta)",
            nativeQuery = true)
    int asignarEtiqueta(@Param("idContenido") Long idContenido, @Param("idEtiqueta") Long idEtiqueta);

    /**
     * Metodo que consiste en designar una etiqueta a un contenido registrado en la DB
     * @param idContenido contenido al que designarle esta etiquita
     * @param idEtiqueta la etiqueta a designar
     * @return si la operacion exitosa
     */
    @Modifying
    @Query(value = "DELETE FROM contenido_etiqueta WHERE ID_contenido = :idContenido AND ID_etiqueta = :idEtiqueta",
            nativeQuery = true)
    int desasignarEtiqueta(@Param("idContenido") Long idContenido, @Param("idEtiqueta") Long idEtiqueta);
}
