package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaAudiovisual;
import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import com.starcritic.dam_proyectspringboot.repository.CriticaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class CriticaService {

    private final CriticaRepository criticaRepository;

    public CriticaService(CriticaRepository criticaRepository) {
        this.criticaRepository = criticaRepository;
    }

    /**
     * Eliminar una critica de la base de datos por su identificador.
     * @param id el identificador unico de la critica.
     */
    @Transactional
    public void eliminarPorId(Long id) {
        criticaRepository.deleteById(id);
    }

    /**
     * Obtener todas las criticas de un aspecto concreto de un contenido audiovisual.
     * @param idAspecto el identificador del aspecto.
     * @param idContenido el identificador del contenido audiovisual.
     * @return las críticas en formato lista.
     */
    public List<CriticaAudiovisual> obtenerCriticasAudiovisualPorAspecto(Long idAspecto, Long idContenido) {
        return criticaRepository.obtenerCriticasAudiovisualPorAspecto(idAspecto, idContenido);
    }

    /**
     * Obtener todas las criticas de un aspecto concreto de un videojuego.
     * @param idAspecto el identificador del aspecto.
     * @param idVideojuego el identificador del videojuego.
     * @return las críticas en formato lista.
     */
    public List<CriticaVideojuego> obtenerCriticasVideojuegoPorAspecto(Long idAspecto, Long idVideojuego) {
        return criticaRepository.obtenerCriticasVideojuegoPorAspecto(idAspecto, idVideojuego);
    }

    /**
     * Obtener todas las criticas de un aspecto hechas por un usuario a contenidos audiovisuales.
     * @param idAspecto el identificador del aspecto.
     * @param idUsuario el identificador del usuario autor de las criticas.
     * @return las críticas en formato lista.
     */
    public List<CriticaAudiovisual> obtenerCriticasAudiovisualPorUsuario(Long idAspecto, Long idUsuario) {
        return criticaRepository.obtenerCriticasAudiovisualPorUsuario(idAspecto, idUsuario);
    }

    /**
     * Obtener todas las criticas de un aspecto hechas por un usuario a videojuegos.
     * @param idAspecto el identificador del aspecto.
     * @param idUsuario el identificador del usuario autor de las criticas.
     * @return las críticas en formato lista.
     */
    public List<CriticaVideojuego> obtenerCriticasVideojuegoPorUsuario(Long idAspecto, Long idUsuario) {
        return criticaRepository.obtenerCriticasVideojuegoPorUsuario(idAspecto, idUsuario);
    }

    /**
     * Comprueba si una critica le pertenece a un usuario en concreto.
     * @param idUsuario el identificador del usuario.
     * @param idCritica el identificador de la critica.
     * @return true si la critica pertenece al usuario, false en caso contrario.
     */
    public boolean esCriticaUsuario(Long idUsuario, Long idCritica) {
        return criticaRepository.esCriticaUsuario(idUsuario, idCritica);
    }
}
