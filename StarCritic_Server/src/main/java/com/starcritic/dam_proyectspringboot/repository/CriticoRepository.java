package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.Critico;
import com.starcritic.dam_proyectspringboot.model.bd.EstadoCertificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CriticoRepository extends JpaRepository<Critico, Long> {
    /**
     * Devuelve todos los criticos que se encuentren en un determinado estado
     * @param estado el estado en el que se desea que se encuentren los criticps
     * @return todos los críticos en ese estado en formato lista
     */
    List<Critico> findByEstado(EstadoCertificacion estado);
    /**
     * Devuelve todos los criticos que se encuentren pendientes, pero siendo un metodo
     * al que pueden acceder las clases que implementen esta interfaz
     * @return todos los críticos pendiente en formato lista
     */
    default List<Critico> obtenerCertificacionesPendientes() {
        return findByEstado(EstadoCertificacion.PENDIENTE);
    }

    /**
     * Inserta la fila de la subclase critico para un usuario_registrado
     * que ya existe (promocion). Con herencia JOINED, JPA no puede convertir un
     * supertipo existente en subtipo, asi que el INSERT se hace nativo.
     * @param id el id del usuario registrado que pasa a ser critico
     * @param certificacion normalmente sera null pero es la clave la cual dara acceso
     * a la nube para obtener su archivo de certificación
     * @param estado el estado de validación de ese critico
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "INSERT INTO critico (ID_critico, certificacion, estado_certificacion) "
            + "VALUES (:id, :certificacion, :estado)", nativeQuery = true)
    void insertarCritico(@Param("id") Long id,@Param("certificacion") String certificacion,@Param("estado") String estado);
}
