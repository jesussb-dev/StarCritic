package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaAudiovisual;
import com.starcritic.dam_proyectspringboot.repository.CriticaAudiovisualRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriticaAudiovisualService {

    private final CriticaAudiovisualRepository criticaAudiovisualRepository;

    public CriticaAudiovisualService(CriticaAudiovisualRepository criticaAudiovisualRepository) {
        this.criticaAudiovisualRepository = criticaAudiovisualRepository;
    }

    @Transactional
    public CriticaAudiovisual guardar(CriticaAudiovisual criticaAudiovisual) {
        return criticaAudiovisualRepository.save(criticaAudiovisual);
    }
}
