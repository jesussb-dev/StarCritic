package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.Mensaje;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Jesús Santos Baquero
 */
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    /**
     * Mensajes recibidos por un usuario, ordenados del mas reciente al mas antiguo.
     * @param idDestinatario el id del usuario destinatario
     * @return la lista de mensajes recibidos
     */
    @Query("SELECT m FROM Mensaje m WHERE m.idDestinatario = :idDestinatario "
            + "ORDER BY m.fechaEnvio DESC")
    List<Mensaje> obtenerMensajesRecibidos(@Param("idDestinatario") Long idDestinatario);
}
