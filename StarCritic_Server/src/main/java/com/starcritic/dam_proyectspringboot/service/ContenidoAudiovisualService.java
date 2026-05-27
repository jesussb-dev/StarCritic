package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.ContenidoAudiovisual;
import com.starcritic.dam_proyectspringboot.repository.ContenidoAudiovisualRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContenidoAudiovisualService {

    private final ContenidoAudiovisualRepository contenidoAudiovisualRepository;

    public ContenidoAudiovisualService(ContenidoAudiovisualRepository contenidoAudiovisualRepository) {
        this.contenidoAudiovisualRepository = contenidoAudiovisualRepository;
    }

    public List<ContenidoAudiovisual> listarTodos() {
        return contenidoAudiovisualRepository.findAll();
    }

    public Optional<ContenidoAudiovisual> buscarPorId(Long id) {
        return contenidoAudiovisualRepository.findById(id);
    }

    @Transactional
    public ContenidoAudiovisual guardar(ContenidoAudiovisual contenidoAudiovisual) {
        return contenidoAudiovisualRepository.save(contenidoAudiovisual);
    }

    public Optional<ContenidoAudiovisual> buscarPorIdOmdb(String idOmdb) {
        return contenidoAudiovisualRepository.findByIdOmdb(idOmdb);
    }

    public boolean existePorIdOmdb(String idOmdb) {
        return contenidoAudiovisualRepository.existsByIdOmdb(idOmdb);
    }
}
