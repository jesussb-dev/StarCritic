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

@RestController
@RequestMapping("/api/rawg")
public class RAWGController {

    private final RAWGService rawgService;

    public RAWGController(RAWGService rawgService) {
        this.rawgService = rawgService;
    }

    @GetMapping("/juegos")
    public ResponseEntity<RAWGListNormal> buscarJuegos(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page) {
        RAWGListNormal result = rawgService.getJuegos(q, page);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<RAWGNormalJson> detalle(@PathVariable int id) {
        RAWGNormalJson result = rawgService.getDetalle(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}
