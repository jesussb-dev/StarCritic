package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Mensaje;
import com.starcritic.dam_proyectspringboot.service.MensajeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    /**
     * Obtener todos los mensajes recibidos por un usuario destinatario concreto.
     * @param idDestinatario el identificador unico del usuario destinatario en la base de datos.
     * @return los mensajes recibidos en formato lista.
     */
    @GetMapping("/destinatario/{idDestinatario}")
    public List<Mensaje> recibidos(@PathVariable Long idDestinatario) {
        return mensajeService.obtenerMensajesRecibidos(idDestinatario);
    }

    /**
     * Crear un mensaje nuevo en la base de datos.
     * @param mensaje el objeto mensaje que se desea insertar.
     * @return el mensaje guardado si la operación fue exitosa.
     */
    @PostMapping
    public Mensaje crear(@RequestBody Mensaje mensaje) {
        mensaje.setIdMensaje(null);
        return mensajeService.guardar(mensaje);
    }

    /**
     * Actualizar un mensaje existente en la base de datos.
     * @param id el identificador unico del mensaje a actualizar.
     * @param mensaje el objeto mensaje con los nuevos datos.
     * @return el mensaje actualizado si la operación fue exitosa.
     */
    @PutMapping("/{id}")
    public Mensaje actualizar(@PathVariable Long id, @RequestBody Mensaje mensaje) {
        mensaje.setIdMensaje(id);
        return mensajeService.guardar(mensaje);
    }
}
