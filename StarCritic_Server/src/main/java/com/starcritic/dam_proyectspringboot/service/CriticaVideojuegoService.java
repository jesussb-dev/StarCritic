package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import com.starcritic.dam_proyectspringboot.repository.CriticaVideojuegoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class CriticaVideojuegoService {

    private final CriticaVideojuegoRepository criticaVideojuegoRepository;

    public CriticaVideojuegoService(CriticaVideojuegoRepository criticaVideojuegoRepository) {
        this.criticaVideojuegoRepository = criticaVideojuegoRepository;
    }

    /**
     * Guardar (crear o actualizar) una critica de un videojuego.
     * @param criticaVideojuego el objeto critica a persistir.
     * @return la critica guardada con su identificador asignado.
     */
    @Transactional
    public CriticaVideojuego guardar(CriticaVideojuego criticaVideojuego) {
        return criticaVideojuegoRepository.save(criticaVideojuego);
    }
}
