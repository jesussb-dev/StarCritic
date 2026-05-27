package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.EtiquetaEditorial;
import com.starcritic.dam_proyectspringboot.service.EtiquetaEditorialService;
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
@RequestMapping("/api/etiquetas")
public class EtiquetaEditorialController {

    private final EtiquetaEditorialService etiquetaEditorialService;

    public EtiquetaEditorialController(EtiquetaEditorialService etiquetaEditorialService) {
        this.etiquetaEditorialService = etiquetaEditorialService;
    }

    @GetMapping
    public List<EtiquetaEditorial> listarTodos() {
        return etiquetaEditorialService.listarTodos();
    }

    @GetMapping("/contenido/{idContenido}")
    public List<EtiquetaEditorial> deContenido(@PathVariable Long idContenido) {
        return etiquetaEditorialService.obtenerEtiquetasDe(idContenido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtiquetaEditorial> porId(@PathVariable Long id) {
        return etiquetaEditorialService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EtiquetaEditorial crear(@RequestBody EtiquetaEditorial etiqueta) {
        return etiquetaEditorialService.guardar(etiqueta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        etiquetaEditorialService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/contenido/{idContenido}/etiqueta/{idEtiqueta}")
    public ResponseEntity<Void> asignar(@PathVariable Long idContenido,
                                        @PathVariable Long idEtiqueta) {
        etiquetaEditorialService.asignarEtiqueta(idContenido, idEtiqueta);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/contenido/{idContenido}/etiqueta/{idEtiqueta}")
    public ResponseEntity<Void> desasignar(@PathVariable Long idContenido,
                                           @PathVariable Long idEtiqueta) {
        etiquetaEditorialService.desasignarEtiqueta(idContenido, idEtiqueta);
        return ResponseEntity.noContent().build();
    }
}
