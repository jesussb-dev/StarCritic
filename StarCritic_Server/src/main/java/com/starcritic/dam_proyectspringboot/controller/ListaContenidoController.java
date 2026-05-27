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

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/listas-contenido")
public class ListaContenidoController {

    private final ListaContenidoService listaContenidoService;

    public ListaContenidoController(ListaContenidoService listaContenidoService) {
        this.listaContenidoService = listaContenidoService;
    }

    /**
     * Añadir un contenido a una lista de un usuario.
     * @param listaContenido el objeto que asocia el contenido con la lista del usuario.
     * @return el objeto guardado en caso de exito.
     */
    @PostMapping
    public ListaContenido crear(@RequestBody ListaContenido listaContenido) {
        return listaContenidoService.guardar(listaContenido);
    }

    /**
     * Obtener todos los contenidos almacenados en una lista concreta de un usuario.
     * @param idUsuario el identificador unico del usuario en la base de datos.
     * @param nombreLista el nombre de la lista del usuario.
     * @return los contenidos de esa lista en formato lista.
     */
    @GetMapping("/usuario/{idUsuario}/lista/{nombreLista}")
    public List<ListaContenido> contenidoDeLista(@PathVariable Long idUsuario,
                                                 @PathVariable String nombreLista) {
        return listaContenidoService.obtenerContenidoDeLista(idUsuario, nombreLista);
    }

    /**
     * Comprueba si un contenido se encuentra en una lista concreta de un usuario.
     * @param idUsuario el identificador unico del usuario en la base de datos.
     * @param nombreLista el nombre de la lista del usuario.
     * @param idContenido el identificador del contenido en la base de datos.
     * @return true si el contenido pertenece a la lista, false en caso contrario.
     */
    @GetMapping("/usuario/{idUsuario}/lista/{nombreLista}/contenido/{idContenido}/existe")
    public boolean existe(@PathVariable Long idUsuario,
                          @PathVariable String nombreLista,
                          @PathVariable Long idContenido) {
        return listaContenidoService.existeContenidoEnLista(idUsuario, nombreLista, idContenido);
    }

    /**
     * Eliminar un contenido de una lista concreta de un usuario.
     * @param idUsuario el identificador unico del usuario en la base de datos.
     * @param nombreLista el nombre de la lista del usuario.
     * @param idContenido el identificador del contenido en la base de datos.
     * @return no devolvera nada en caso de exito, en caso de error la respuesta del error.
     */
    @DeleteMapping("/usuario/{idUsuario}/lista/{nombreLista}/contenido/{idContenido}")
    public ResponseEntity<Void> eliminarContenido(@PathVariable Long idUsuario,
                                                  @PathVariable String nombreLista,
                                                  @PathVariable Long idContenido) {
        int filas = listaContenidoService.eliminarContenidoDeLista(idUsuario, nombreLista, idContenido);
        return filas > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
