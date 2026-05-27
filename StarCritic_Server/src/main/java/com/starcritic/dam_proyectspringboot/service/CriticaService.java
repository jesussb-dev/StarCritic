package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaAudiovisual;
import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import com.starcritic.dam_proyectspringboot.repository.CriticaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriticaService {

    private final CriticaRepository criticaRepository;

    public CriticaService(CriticaRepository criticaRepository) {
        this.criticaRepository = criticaRepository;
    }

    @Transactional
    public void eliminarPorId(Long id) {
        criticaRepository.deleteById(id);
    }

    public List<CriticaAudiovisual> obtenerCriticasAudiovisualPorAspecto(Long idAspecto, Long idContenido) {
        return criticaRepository.obtenerCriticasAudiovisualPorAspecto(idAspecto, idContenido);
    }

    public List<CriticaVideojuego> obtenerCriticasVideojuegoPorAspecto(Long idAspecto, Long idVideojuego) {
        return criticaRepository.obtenerCriticasVideojuegoPorAspecto(idAspecto, idVideojuego);
    }

    public List<CriticaAudiovisual> obtenerCriticasAudiovisualPorUsuario(Long idAspecto, Long idUsuario) {
        return criticaRepository.obtenerCriticasAudiovisualPorUsuario(idAspecto, idUsuario);
    }

    public List<CriticaVideojuego> obtenerCriticasVideojuegoPorUsuario(Long idAspecto, Long idUsuario) {
        return criticaRepository.obtenerCriticasVideojuegoPorUsuario(idAspecto, idUsuario);
    }

    public boolean esCriticaUsuario(Long idUsuario, Long idCritica) {
        return criticaRepository.esCriticaUsuario(idUsuario, idCritica);
    }
}
