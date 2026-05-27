package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.Mensaje;
import com.starcritic.dam_proyectspringboot.repository.MensajeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    /**
     * Guardar (crear o actualizar) un mensaje en la base de datos.
     * @param mensaje el objeto mensaje a persistir.
     * @return el mensaje guardado con su identificador asignado.
     */
    @Transactional
    public Mensaje guardar(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    /**
     * Mensajes recibidos por un usuario, del mas reciente al mas antiguo.
     * @param idDestinatario el identificador del usuario destinatario.
     * @return los mensajes recibidos en formato lista.
     */
    public List<Mensaje> obtenerMensajesRecibidos(Long idDestinatario) {
        return mensajeRepository.obtenerMensajesRecibidos(idDestinatario);
    }
}
