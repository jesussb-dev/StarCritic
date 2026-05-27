package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.Videojuego;
import com.starcritic.dam_proyectspringboot.repository.VideojuegoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;

    public VideojuegoService(VideojuegoRepository videojuegoRepository) {
        this.videojuegoRepository = videojuegoRepository;
    }

    public List<Videojuego> listarTodos() {
        return videojuegoRepository.findAll();
    }

    public Optional<Videojuego> buscarPorId(Long id) {
        return videojuegoRepository.findById(id);
    }

    @Transactional
    public Videojuego guardar(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    public Optional<Videojuego> buscarPorIdRawg(int idRawg) {
        return videojuegoRepository.findByIdRawg(idRawg);
    }

    public boolean existePorIdRawg(int idRawg) {
        return videojuegoRepository.existsByIdRawg(idRawg);
    }
}
