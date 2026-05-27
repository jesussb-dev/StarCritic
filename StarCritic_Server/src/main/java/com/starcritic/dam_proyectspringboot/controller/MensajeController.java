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

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping("/destinatario/{idDestinatario}")
    public List<Mensaje> recibidos(@PathVariable Long idDestinatario) {
        return mensajeService.obtenerMensajesRecibidos(idDestinatario);
    }

    @PostMapping
    public Mensaje crear(@RequestBody Mensaje mensaje) {
        mensaje.setIdMensaje(null);
        return mensajeService.guardar(mensaje);
    }

    @PutMapping("/{id}")
    public Mensaje actualizar(@PathVariable Long id, @RequestBody Mensaje mensaje) {
        mensaje.setIdMensaje(id);
        return mensajeService.guardar(mensaje);
    }
}
