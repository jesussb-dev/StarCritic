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

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/omdb")
public class OMDbController {

    private final OMDbService omDbService;

    public OMDbController(OMDbService omDbService) {
        this.omDbService = omDbService;
    }

    /**
     * Busca peliculas en la API externa OMDb por nombre y pagina.
     * @param q el texto de busqueda introducido por el usuario.
     * @param page el número de pagina de resultados (por defecto 1).
     * @return el listado de resultados de peliculas, o un 400 si no hay resultados.
     */
    @GetMapping("/peliculas")
    public ResponseEntity<OMDbListSearch> buscarPeliculas(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page) {
        OMDbListSearch result = omDbService.getPeliculas(q, page);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    /**
     * Busca series en la API externa OMDb por nombre y pagina.
     * @param q el texto de busqueda introducido por el usuario.
     * @param page el número de pagina de resultados (por defecto 1).
     * @return el listado de resultados de series, o un 400 si no hay resultados.
     */
    @GetMapping("/series")
    public ResponseEntity<OMDbListSearch> buscarSeries(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page) {
        OMDbListSearch result = omDbService.getSeries(q, page);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    /**
     * Obtiene el detalle completo de un contenido de OMDb a partir de su identificador IMDb.
     * @param imdbId el identificador del contenido en IMDb/OMDb.
     * @return el detalle del contenido en caso de exito, 404 si no existe.
     */
    @GetMapping("/detalle/{imdbId}")
    public ResponseEntity<OMDbDetailJson> detalle(@PathVariable String imdbId) {
        OMDbDetailJson result = omDbService.getDetalle(imdbId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}
