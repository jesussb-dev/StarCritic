package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.Videojuego;
import com.starcritic.dam_proyectspringboot.repository.VideojuegoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;

    public VideojuegoService(VideojuegoRepository videojuegoRepository) {
        this.videojuegoRepository = videojuegoRepository;
    }

    /**
     * Obtener todos los videojuegos guardados en la base de datos.
     * @return los videojuegos en formato lista.
     */
    public List<Videojuego> listarTodos() {
        return videojuegoRepository.findAll();
    }

    /**
     * Obtener un videojuego por su identificador propio de la base de datos.
     * @param id el identificador unico en la base de datos.
     * @return el videojuego si existe, en caso contrario un Optional vacio.
     */
    public Optional<Videojuego> buscarPorId(Long id) {
        return videojuegoRepository.findById(id);
    }

    /**
     * Guardar (crear o actualizar) un videojuego en la base de datos.
     * @param videojuego el objeto videojuego a persistir.
     * @return el videojuego guardado con su identificador asignado.
     */
    @Transactional
    public Videojuego guardar(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    /**
     * Obtener un videojuego a través del identificador de la API externa RAWG.
     * @param idRawg el identificador del videojuego en RAWG.
     * @return el videojuego si existe, en caso contrario un Optional vacio.
     */
    public Optional<Videojuego> buscarPorIdRawg(int idRawg) {
        return videojuegoRepository.findByIdRawg(idRawg);
    }

    /**
     * Comprueba si en la base de datos existe un videojuego con ese identificador de RAWG.
     * @param idRawg el identificador del videojuego en RAWG.
     * @return true si existe, false en caso contrario.
     */
    public boolean existePorIdRawg(int idRawg) {
        return videojuegoRepository.existsByIdRawg(idRawg);
    }
}
