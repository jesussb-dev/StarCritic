package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.ContenidoUsuario;
import com.starcritic.dam_proyectspringboot.model.bd.ContenidoUsuarioId;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Jesús Santos Baquero
 */
@Repository
public interface ContenidoUsuarioRepository extends JpaRepository<ContenidoUsuario, ContenidoUsuarioId> {

    /**
     * Registra una visita: inserta la fila con num_visitas = 1 o, si ya existe,
     * incrementa el contador y actualiza la fecha (upsert MySQL).
     * @param idUsuario el usuario que visita el contenido
     * @param idContenido el contenido visitado por el usuario
     * @param fechaVisita la fecha cuanto ha sido registrado
     */
    @Modifying
    @Query(value = "INSERT INTO contenido_usuario (ID_usuario_registrado, ID_contenido, fecha_visita, num_visitas) "
            + "VALUES (:idUsuario, :idContenido, :fechaVisita, 1) "
            + "ON DUPLICATE KEY UPDATE num_visitas = num_visitas + 1, fecha_visita = VALUES(fecha_visita)",
            nativeQuery = true)
    void registrarVisita(@Param("idUsuario") Long idUsuario,@Param("idContenido") Long idContenido,@Param("fechaVisita") LocalDate fechaVisita);
}
