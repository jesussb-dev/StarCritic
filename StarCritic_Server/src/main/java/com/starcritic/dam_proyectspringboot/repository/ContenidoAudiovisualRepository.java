package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.ContenidoAudiovisual;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jesús Santos Baquero
 */
@Repository
public interface ContenidoAudiovisualRepository extends JpaRepository<ContenidoAudiovisual, Long> {

    /**
     * Busca por el identificador de la API externa (OMDb).
     * @param idOmdb el identificador de la API externa
     * @return el contenido audiovisual en el formato correcto
     */
    Optional<ContenidoAudiovisual> findByIdOmdb(String idOmdb);
    
    /**
     * @param idOmdb la clave propia de la API de idOmdb
     * @return  si existe un contenido audivisal en la base de datos con la
     * misma clave propia
     */
    boolean existsByIdOmdb(String idOmdb);
}
