package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.Mensaje;
import com.starcritic.dam_proyectspringboot.repository.MensajeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    @Transactional
    public Mensaje guardar(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    /** Mensajes recibidos por un usuario, del mas reciente al mas antiguo. */
    public List<Mensaje> obtenerMensajesRecibidos(Long idDestinatario) {
        return mensajeRepository.obtenerMensajesRecibidos(idDestinatario);
    }
}
