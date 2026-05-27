package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Aspecto;
import com.starcritic.dam_proyectspringboot.service.AspectoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aspectos")
public class AspectoController {

    private final AspectoService aspectoService;

    public AspectoController(AspectoService aspectoService) {
        this.aspectoService = aspectoService;
    }

    @GetMapping
    public List<Aspecto> listarTodos() {
        return aspectoService.listarTodos();
    }

    @GetMapping("/audiovisual")
    public List<Aspecto> audiovisual() {
        return aspectoService.obtenerAspectosAudiovisual();
    }

    @GetMapping("/videojuego")
    public List<Aspecto> videojuego() {
        return aspectoService.obtenerAspectosVideojuego();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aspecto> porId(@PathVariable Long id) {
        return aspectoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
