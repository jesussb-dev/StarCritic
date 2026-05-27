package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.Contenido;
import com.starcritic.dam_proyectspringboot.repository.ContenidoRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContenidoService {

    private final ContenidoRepository contenidoRepository;

    public ContenidoService(ContenidoRepository contenidoRepository) {
        this.contenidoRepository = contenidoRepository;
    }

    public Optional<Contenido> buscarPorId(Long id) {
        return contenidoRepository.findById(id);
    }

    @Transactional
    public void eliminarPorId(Long id) {
        contenidoRepository.deleteById(id);
    }

    public double mediaAspectoAudiovisual(Long idContenido, Long idAspecto) {
        return contenidoRepository.mediaAspectoAudiovisual(idContenido, idAspecto);
    }

    public double mediaAspectoVideojuego(Long idVideojuego, Long idAspecto) {
        return contenidoRepository.mediaAspectoVideojuego(idVideojuego, idAspecto);
    }

    @Transactional
    public int actualizarOculto(Long idContenido, boolean oculto) {
        return contenidoRepository.actualizarOculto(idContenido, oculto);
    }

    @Transactional
    public int actualizarDestacado(Long idContenido, boolean destacado) {
        return contenidoRepository.actualizarDestacado(idContenido, destacado);
    }
}
