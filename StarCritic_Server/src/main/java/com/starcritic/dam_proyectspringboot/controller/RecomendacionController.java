package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.TipoContenido;
import com.starcritic.dam_proyectspringboot.repository.projection.RecommendedItemView;
import com.starcritic.dam_proyectspringboot.service.RecomendacionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/recomendaciones")
public class RecomendacionController {

    private final RecomendacionService recomendacionService;

    public RecomendacionController(RecomendacionService recomendacionService) {
        this.recomendacionService = recomendacionService;
    }

    /**
     * Recomendaciones del tipo indicado. Si {@code idUsuario} es null o negativo
     * se usa la popularidad global; en otro caso, recomendaciones personalizadas.
     * @param tipo el tipo de contenido a recomendar (PELICULA, SERIE, VIDEOJUEGO).
     * @param idUsuario el identificador del usuario para personalizar las recomendaciones,
     * opcional.
     * @param limite el nº máximo de recomendaciones a devolver (por defecto 10).
     * @return las recomendaciones en formato lista.
     */
    @GetMapping
    public List<RecommendedItemView> recomendaciones(
            @RequestParam TipoContenido tipo,
            @RequestParam(required = false) Long idUsuario,
            @RequestParam(defaultValue = "10") int limite) {
        return recomendacionService.obtenerRecomendaciones(tipo, idUsuario, limite);
    }
}
