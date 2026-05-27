package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Videojuego;
import com.starcritic.dam_proyectspringboot.service.VideojuegoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping
    public List<Videojuego> listarTodos() {
        return videojuegoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> porId(@PathVariable Long id) {
        return videojuegoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rawg/{idRawg}")
    public ResponseEntity<Videojuego> porIdRawg(@PathVariable int idRawg) {
        return videojuegoService.buscarPorIdRawg(idRawg)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rawg/{idRawg}/existe")
    public boolean existeRawg(@PathVariable int idRawg) {
        return videojuegoService.existePorIdRawg(idRawg);
    }

    @PostMapping
    public Videojuego crear(@RequestBody Videojuego videojuego) {
        if (videojuego.getIdContenido() != null && videojuego.getIdContenido() == 0L) {
            videojuego.setIdContenido(null);
        }
        return videojuegoService.guardar(videojuego);
    }
}
