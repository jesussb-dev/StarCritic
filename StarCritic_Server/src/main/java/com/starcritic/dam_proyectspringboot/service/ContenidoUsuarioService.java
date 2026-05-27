package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.repository.ContenidoUsuarioRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class ContenidoUsuarioService {

    private final ContenidoUsuarioRepository contenidoUsuarioRepository;

    public ContenidoUsuarioService(ContenidoUsuarioRepository contenidoUsuarioRepository) {
        this.contenidoUsuarioRepository = contenidoUsuarioRepository;
    }

    /**
     * Registra una visita (upsert: inserta o incrementa el contador).
     * @param idUsuario el usuario que visita el contenido.
     * @param idContenido el contenido visitado por el usuario.
     * @param fechaVisita la fecha en la que se ha registrado la visita.
     */
    @Transactional
    public void registrarVisita(Long idUsuario, Long idContenido, LocalDate fechaVisita) {
        contenidoUsuarioRepository.registrarVisita(idUsuario, idContenido, fechaVisita);
    }
}
