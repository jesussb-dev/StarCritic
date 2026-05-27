package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.ListaContenido;
import com.starcritic.dam_proyectspringboot.service.ListaContenidoService;
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
@RequestMapping("/api/listas-contenido")
public class ListaContenidoController {

    private final ListaContenidoService listaContenidoService;

    public ListaContenidoController(ListaContenidoService listaContenidoService) {
        this.listaContenidoService = listaContenidoService;
    }

    @PostMapping
    public ListaContenido crear(@RequestBody ListaContenido listaContenido) {
        return listaContenidoService.guardar(listaContenido);
    }

    @GetMapping("/usuario/{idUsuario}/lista/{nombreLista}")
    public List<ListaContenido> contenidoDeLista(@PathVariable Long idUsuario,
                                                 @PathVariable String nombreLista) {
        return listaContenidoService.obtenerContenidoDeLista(idUsuario, nombreLista);
    }

    @GetMapping("/usuario/{idUsuario}/lista/{nombreLista}/contenido/{idContenido}/existe")
    public boolean existe(@PathVariable Long idUsuario,
                          @PathVariable String nombreLista,
                          @PathVariable Long idContenido) {
        return listaContenidoService.existeContenidoEnLista(idUsuario, nombreLista, idContenido);
    }

    @DeleteMapping("/usuario/{idUsuario}/lista/{nombreLista}/contenido/{idContenido}")
    public ResponseEntity<Void> eliminarContenido(@PathVariable Long idUsuario,
                                                  @PathVariable String nombreLista,
                                                  @PathVariable Long idContenido) {
        int filas = listaContenidoService.eliminarContenidoDeLista(idUsuario, nombreLista, idContenido);
        return filas > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
