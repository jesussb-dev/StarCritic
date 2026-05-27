package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.Aspecto;
import com.starcritic.dam_proyectspringboot.repository.AspectoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class AspectoService {

    private final AspectoRepository aspectoRepository;

    public AspectoService(AspectoRepository aspectoRepository) {
        this.aspectoRepository = aspectoRepository;
    }
    /**
     * Obtener todos los aspectos
     * @return todos los aspectos en la BD
     */
    public List<Aspecto> listarTodos() {
        return aspectoRepository.findAll();
    }
    /**
     * Obtener un aspecto por su id de la BD
     * @param id identificador propio de la BD
     * @return devolvera el aspecto y si no lo encuentra null
     */
    public Optional<Aspecto> buscarPorId(Long id) {
        return aspectoRepository.findById(id);
    }
    /**
     * Obtener todos los aspectos de un contenido audiovisual
     * @return la lista de todos los aspectos que puede tener un
     * contenido audiovisual
     */
    public List<Aspecto> obtenerAspectosAudiovisual() {
        return aspectoRepository.obtenerAspectosAudiovisual();
    }
    /**
     * Obtener todos los aspectos de un contenido videojuego
     * @return la lista de todos los aspectos que puede tener un
     * contenido videojuego
     */
    public List<Aspecto> obtenerAspectosVideojuego() {
        return aspectoRepository.obtenerAspectosVideojuego();
    }
}
