package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.service.EstadisticasService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    public EstadisticasController(EstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    // ===================== PERSONALES ===================== //

    @GetMapping("/usuario/{idUsuario}/contenido-en-listas")
    public Map<String, Double> contenidoEnListas(@PathVariable Long idUsuario) {
        return estadisticasService.contenidoEnListasPorTipo(idUsuario);
    }

    @GetMapping("/usuario/{idUsuario}/media-por-aspecto")
    public Map<String, Double> mediaPorAspecto(@PathVariable Long idUsuario) {
        return estadisticasService.mediaPorAspectoUsuario(idUsuario);
    }

    @GetMapping("/usuario/{idUsuario}/visitas-por-mes")
    public Map<String, Double> visitasPorMes(@PathVariable Long idUsuario) {
        return estadisticasService.visitasPorMesUsuario(idUsuario);
    }


    // ===================== GENERALES ===================== //

    @GetMapping("/top-visitado")
    public Map<String, Double> topVisitado(@RequestParam(defaultValue = "10") int limite) {
        return estadisticasService.topContenidoMasVisitado(limite);
    }



    @GetMapping("/catalogo-por-origen")
    public Map<String, Double> catalogoPorOrigen() {
        return estadisticasService.catalogoPorOrigen();
    }



    @GetMapping("/distribucion-puntuaciones")
    public Map<String, Double> distribucionPuntuaciones() {
        return estadisticasService.distribucionPuntuaciones();
    }

}
