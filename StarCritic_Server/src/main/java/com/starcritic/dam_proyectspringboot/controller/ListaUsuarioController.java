package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.ListaUsuario;
import com.starcritic.dam_proyectspringboot.service.ListaUsuarioService;
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
@RequestMapping("/api/listas-usuario")
public class ListaUsuarioController {

    private final ListaUsuarioService listaUsuarioService;

    public ListaUsuarioController(ListaUsuarioService listaUsuarioService) {
        this.listaUsuarioService = listaUsuarioService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<ListaUsuario> deUsuario(@PathVariable Long idUsuario) {
        return listaUsuarioService.obtenerListasDeUsuario(idUsuario);
    }

    @PostMapping
    public ListaUsuario crear(@RequestBody ListaUsuario listaUsuario) {
        return listaUsuarioService.guardar(listaUsuario);
    }

    @DeleteMapping("/usuario/{idUsuario}/lista/{nombreLista}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idUsuario,
                                         @PathVariable String nombreLista) {
        listaUsuarioService.eliminarLista(idUsuario, nombreLista);
        return ResponseEntity.noContent().build();
    }
}
