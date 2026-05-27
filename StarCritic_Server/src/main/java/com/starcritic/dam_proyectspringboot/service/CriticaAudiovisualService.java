package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaAudiovisual;
import com.starcritic.dam_proyectspringboot.repository.CriticaAudiovisualRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class CriticaAudiovisualService {

    private final CriticaAudiovisualRepository criticaAudiovisualRepository;

    public CriticaAudiovisualService(CriticaAudiovisualRepository criticaAudiovisualRepository) {
        this.criticaAudiovisualRepository = criticaAudiovisualRepository;
    }

    /**
     * Guardar (crear o actualizar) una critica de un contenido audiovisual.
     * @param criticaAudiovisual el objeto critica a persistir.
     * @return la critica guardada con su identificador asignado.
     */
    @Transactional
    public CriticaAudiovisual guardar(CriticaAudiovisual criticaAudiovisual) {
        return criticaAudiovisualRepository.save(criticaAudiovisual);
    }
}
