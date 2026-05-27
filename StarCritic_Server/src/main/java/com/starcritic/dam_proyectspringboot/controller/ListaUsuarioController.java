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


/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/listas-usuario")
public class ListaUsuarioController {

    private final ListaUsuarioService listaUsuarioService;

    public ListaUsuarioController(ListaUsuarioService listaUsuarioService) {
        this.listaUsuarioService = listaUsuarioService;
    }

    /**
     * Obtener todas las listas que un usuario ha creado.
     * @param idUsuario el identificador unico del usuario en la base de datos.
     * @return las listas del usuario en formato lista.
     */
    @GetMapping("/usuario/{idUsuario}")
    public List<ListaUsuario> deUsuario(@PathVariable Long idUsuario) {
        return listaUsuarioService.obtenerListasDeUsuario(idUsuario);
    }

    /**
     * Crear una nueva lista para un usuario.
     * @param listaUsuario el objeto lista que se desea insertar.
     * @return la lista guardada si la operación fue exitosa.
     */
    @PostMapping
    public ListaUsuario crear(@RequestBody ListaUsuario listaUsuario) {
        return listaUsuarioService.guardar(listaUsuario);
    }

    /**
     * Eliminar una lista de un usuario junto con todos los contenidos asociados.
     * @param idUsuario el identificador unico del usuario en la base de datos.
     * @param nombreLista el nombre de la lista a eliminar.
     * @return no devolvera nada en caso de exito, en caso de error la respuesta del error.
     */
    @DeleteMapping("/usuario/{idUsuario}/lista/{nombreLista}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idUsuario,
                                         @PathVariable String nombreLista) {
        listaUsuarioService.eliminarLista(idUsuario, nombreLista);
        return ResponseEntity.noContent().build();
    }
}
