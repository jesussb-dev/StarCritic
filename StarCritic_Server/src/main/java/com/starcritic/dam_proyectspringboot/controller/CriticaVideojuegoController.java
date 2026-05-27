package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import com.starcritic.dam_proyectspringboot.service.CriticaVideojuegoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/criticas-videojuegos")
public class CriticaVideojuegoController {

    private final CriticaVideojuegoService criticaVideojuegoService;

    public CriticaVideojuegoController(CriticaVideojuegoService criticaVideojuegoService) {
        this.criticaVideojuegoService = criticaVideojuegoService;
    }

    @PostMapping
    public CriticaVideojuego crear(@RequestBody CriticaVideojuego criticaVideojuego) {
        return criticaVideojuegoService.guardar(criticaVideojuego);
    }
}
