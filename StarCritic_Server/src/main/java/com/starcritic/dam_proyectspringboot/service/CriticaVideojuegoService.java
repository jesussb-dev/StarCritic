package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import com.starcritic.dam_proyectspringboot.repository.CriticaVideojuegoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriticaVideojuegoService {

    private final CriticaVideojuegoRepository criticaVideojuegoRepository;

    public CriticaVideojuegoService(CriticaVideojuegoRepository criticaVideojuegoRepository) {
        this.criticaVideojuegoRepository = criticaVideojuegoRepository;
    }

    @Transactional
    public CriticaVideojuego guardar(CriticaVideojuego criticaVideojuego) {
        return criticaVideojuegoRepository.save(criticaVideojuego);
    }
}
