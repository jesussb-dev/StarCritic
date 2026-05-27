package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaAudiovisual;
import com.starcritic.dam_proyectspringboot.service.CriticaAudiovisualService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/criticas-audiovisuales")
public class CriticaAudiovisualController {

    private final CriticaAudiovisualService criticaAudiovisualService;

    public CriticaAudiovisualController(CriticaAudiovisualService criticaAudiovisualService) {
        this.criticaAudiovisualService = criticaAudiovisualService;
    }
    /**
     * Crear una crítica para un un aspecto de un contenido audiovisual.
     * @param criticaAudiovisual el objeto critica audiovisual que se desea insertar.
     * @return devuelve la critica si la operación fue exitosa.
     */
    @PostMapping
    public CriticaAudiovisual crear(@RequestBody CriticaAudiovisual criticaAudiovisual) {
        return criticaAudiovisualService.guardar(criticaAudiovisual);
    }
}
