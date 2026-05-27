package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.Videojuego;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    /**
     * Busca por el identificador de la API externa (RAWG).
     * @param idRawg el id propio de la API externa
     * @return devuelve el objeto si existe y si no devuelve null
     */
    Optional<Videojuego> findByIdRawg(int idRawg);
    /**
     * Comprueba si el contenido existe
     * @param idRawg el id propio de la API externa
     * @return devuelve si existe o no el contenido
     */
    boolean existsByIdRawg(int idRawg);
}
