package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.repository.ContenidoUsuarioRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContenidoUsuarioService {

    private final ContenidoUsuarioRepository contenidoUsuarioRepository;

    public ContenidoUsuarioService(ContenidoUsuarioRepository contenidoUsuarioRepository) {
        this.contenidoUsuarioRepository = contenidoUsuarioRepository;
    }

    /** Registra una visita (upsert: inserta o incrementa el contador). */
    @Transactional
    public void registrarVisita(Long idUsuario, Long idContenido, LocalDate fechaVisita) {
        contenidoUsuarioRepository.registrarVisita(idUsuario, idContenido, fechaVisita);
    }
}
