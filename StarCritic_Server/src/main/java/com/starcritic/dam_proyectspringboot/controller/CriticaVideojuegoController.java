package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import com.starcritic.dam_proyectspringboot.service.CriticaVideojuegoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/criticas-videojuegos")
public class CriticaVideojuegoController {

    private final CriticaVideojuegoService criticaVideojuegoService;

    public CriticaVideojuegoController(CriticaVideojuegoService criticaVideojuegoService) {
        this.criticaVideojuegoService = criticaVideojuegoService;
    }
    
    /**
     * Crear una critica de un aspecto de un videojuego.
     * @param criticaVideojuego el objeto de una critica a un videojuego.
     * @return devolvera el mismo objeto en caso de exito.
     */
    @PostMapping
    public CriticaVideojuego crear(@RequestBody CriticaVideojuego criticaVideojuego) {
        return criticaVideojuegoService.guardar(criticaVideojuego);
    }
}
