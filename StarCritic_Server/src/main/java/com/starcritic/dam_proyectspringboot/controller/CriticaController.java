package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Critica;
import com.starcritic.dam_proyectspringboot.model.bd.CriticaAudiovisual;
import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import com.starcritic.dam_proyectspringboot.service.CriticaService;
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
@RequestMapping("/api/criticas")
public class CriticaController {

    private final CriticaService criticaService;

    public CriticaController(CriticaService criticaService) {
        this.criticaService = criticaService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        criticaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/audiovisual/aspecto/{idAspecto}/contenido/{idContenido}")
    public List<CriticaAudiovisual> audiovisualPorAspecto(@PathVariable Long idAspecto,
                                                          @PathVariable Long idContenido) {
        return criticaService.obtenerCriticasAudiovisualPorAspecto(idAspecto, idContenido);
    }

    @GetMapping("/videojuego/aspecto/{idAspecto}/videojuego/{idVideojuego}")
    public List<CriticaVideojuego> videojuegoPorAspecto(@PathVariable Long idAspecto,
                                                        @PathVariable Long idVideojuego) {
        return criticaService.obtenerCriticasVideojuegoPorAspecto(idAspecto, idVideojuego);
    }

    @GetMapping("/audiovisual/aspecto/{idAspecto}/usuario/{idUsuario}")
    public List<CriticaAudiovisual> audiovisualPorUsuario(@PathVariable Long idAspecto,
                                                          @PathVariable Long idUsuario) {
        return criticaService.obtenerCriticasAudiovisualPorUsuario(idAspecto, idUsuario);
    }

    @GetMapping("/videojuego/aspecto/{idAspecto}/usuario/{idUsuario}")
    public List<CriticaVideojuego> videojuegoPorUsuario(@PathVariable Long idAspecto,
                                                        @PathVariable Long idUsuario) {
        return criticaService.obtenerCriticasVideojuegoPorUsuario(idAspecto, idUsuario);
    }

    @GetMapping("/{idCritica}/es-de-usuario/{idUsuario}")
    public boolean esCriticaUsuario(@PathVariable Long idCritica,
                                    @PathVariable Long idUsuario) {
        return criticaService.esCriticaUsuario(idUsuario, idCritica);
    }
}
