package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.api.rawg.RAWGListNormal;
import com.starcritic.dam_proyectspringboot.model.api.rawg.RAWGNormalJson;
import com.starcritic.dam_proyectspringboot.service.api.RAWGService;
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
@RequestMapping("/api/rawg")
public class RAWGController {

    private final RAWGService rawgService;

    public RAWGController(RAWGService rawgService) {
        this.rawgService = rawgService;
    }

    /**
     * Busca videojuegos en la API externa RAWG por nombre y pagina.
     * @param q el texto de busqueda introducido por el usuario.
     * @param page el número de pagina de resultados (por defecto 1).
     * @return el listado de resultados de videojuegos, o un 400 si no hay resultados.
     */
    @GetMapping("/juegos")
    public ResponseEntity<RAWGListNormal> buscarJuegos(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page) {
        RAWGListNormal result = rawgService.getJuegos(q, page);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    /**
     * Obtiene el detalle completo de un videojuego de RAWG a partir de su identificador.
     * @param id el identificador unico del videojuego en RAWG.
     * @return el detalle del videojuego en caso de exito, 404 si no existe.
     */
    @GetMapping("/detalle/{id}")
    public ResponseEntity<RAWGNormalJson> detalle(@PathVariable int id) {
        RAWGNormalJson result = rawgService.getDetalle(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}
