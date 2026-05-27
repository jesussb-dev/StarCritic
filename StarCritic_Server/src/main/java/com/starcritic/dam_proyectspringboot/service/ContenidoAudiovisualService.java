package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.ContenidoAudiovisual;
import com.starcritic.dam_proyectspringboot.repository.ContenidoAudiovisualRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class ContenidoAudiovisualService {

    private final ContenidoAudiovisualRepository contenidoAudiovisualRepository;

    public ContenidoAudiovisualService(ContenidoAudiovisualRepository contenidoAudiovisualRepository) {
        this.contenidoAudiovisualRepository = contenidoAudiovisualRepository;
    }

    /**
     * Obtener todos los contenidos audiovisuales guardados en la base de datos.
     * @return los contenidos audiovisuales en formato lista.
     */
    public List<ContenidoAudiovisual> listarTodos() {
        return contenidoAudiovisualRepository.findAll();
    }

    /**
     * Obtener un contenido audiovisual por su identificador propio de la base de datos.
     * @param id el identificador unico en la base de datos.
     * @return el contenido si existe, en caso contrario un Optional vacio.
     */
    public Optional<ContenidoAudiovisual> buscarPorId(Long id) {
        return contenidoAudiovisualRepository.findById(id);
    }

    /**
     * Guardar (crear o actualizar) un contenido audiovisual en la base de datos.
     * @param contenidoAudiovisual el objeto contenido a persistir.
     * @return el contenido guardado con su identificador asignado.
     */
    @Transactional
    public ContenidoAudiovisual guardar(ContenidoAudiovisual contenidoAudiovisual) {
        return contenidoAudiovisualRepository.save(contenidoAudiovisual);
    }

    /**
     * Obtener un contenido audiovisual a través del identificador de la API externa OMDb.
     * @param idOmdb el identificador del contenido en OMDb.
     * @return el contenido si existe, en caso contrario un Optional vacio.
     */
    public Optional<ContenidoAudiovisual> buscarPorIdOmdb(String idOmdb) {
        return contenidoAudiovisualRepository.findByIdOmdb(idOmdb);
    }

    /**
     * Comprueba si en la base de datos existe un contenido audiovisual guardado
     * con ese identificador de OMDb.
     * @param idOmdb el identificador del contenido en OMDb.
     * @return true si existe el contenido, false en caso contrario.
     */
    public boolean existePorIdOmdb(String idOmdb) {
        return contenidoAudiovisualRepository.existsByIdOmdb(idOmdb);
    }
}
