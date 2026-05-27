package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA de criticas hechas a videojuegos. Hereda los metodos CRUD de
 * {@link JpaRepository} para operar sobre las críticas.
 * @author Jesús Santos Baquero
 */
@Repository
public interface CriticaVideojuegoRepository extends JpaRepository<CriticaVideojuego, Long> {
}
