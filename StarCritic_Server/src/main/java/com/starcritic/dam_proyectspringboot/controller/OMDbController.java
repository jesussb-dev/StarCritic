package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.api.omdb.OMDbDetailJson;
import com.starcritic.dam_proyectspringboot.model.api.omdb.OMDbListSearch;
import com.starcritic.dam_proyectspringboot.service.api.OMDbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/omdb")
public class OMDbController {

    private final OMDbService omDbService;

    public OMDbController(OMDbService omDbService) {
        this.omDbService = omDbService;
    }

    @GetMapping("/peliculas")
    public ResponseEntity<OMDbListSearch> buscarPeliculas(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page) {
        OMDbListSearch result = omDbService.getPeliculas(q, page);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/series")
    public ResponseEntity<OMDbListSearch> buscarSeries(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page) {
        OMDbListSearch result = omDbService.getSeries(q, page);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/detalle/{imdbId}")
    public ResponseEntity<OMDbDetailJson> detalle(@PathVariable String imdbId) {
        OMDbDetailJson result = omDbService.getDetalle(imdbId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}
