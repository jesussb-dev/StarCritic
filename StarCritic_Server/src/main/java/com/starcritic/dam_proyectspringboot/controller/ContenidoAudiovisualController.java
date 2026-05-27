package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.ContenidoAudiovisual;
import com.starcritic.dam_proyectspringboot.service.ContenidoAudiovisualService;
import java.util.List;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/contenidos-audiovisuales")
public class ContenidoAudiovisualController {

    private final ContenidoAudiovisualService contenidoAudiovisualService;

    public ContenidoAudiovisualController(ContenidoAudiovisualService contenidoAudiovisualService) {
        this.contenidoAudiovisualService = contenidoAudiovisualService;
    }
    /**
     * Obtiene todos los contenidos audiovisuales guardados en la BD
     * @return la lista de contenidos audiovisuales
     */
    @GetMapping
    public List<ContenidoAudiovisual> listarTodos() {
        return contenidoAudiovisualService.listarTodos();
    }
    

    /**
     * Obtener un contenido audiovisual por su id propio de la BD
     * @param id el id de la BD
     * @return el objeto guardado en la BD con su respectivo formato
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContenidoAudiovisual> porId(@PathVariable Long id) {
        return contenidoAudiovisualService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Obtener el contenido a traves del id guardado de la API externa
     * @param idOmdb el id de la API externa
     * @return el objeto guardado en la BD con su respectivo formato
     */
    @GetMapping("/omdb/{idOmdb}")
    public ResponseEntity<ContenidoAudiovisual> porIdOmdb(@PathVariable String idOmdb) {
        return contenidoAudiovisualService.buscarPorIdOmdb(idOmdb)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Comprueba si en la BD existe un contenido guardado con ese id de la API externo
     * @param idOmdb el id de la API externa
     * @return si existe o no el contenido
     */
    @GetMapping("/omdb/{idOmdb}/existe")
    public boolean existeOmdb(@PathVariable String idOmdb) {
        return contenidoAudiovisualService.existePorIdOmdb(idOmdb);
    }
    
    /**
     * Crea un contenido o lo "guarda" en la BD, ya que si no es Local solo serán
     * los datos que se mostraran en la aplicacición
     * @param contenidoAudiovisual los datos del contenido en el formato solcitado
     * @return si se ha creado correctamente.
     */
    @PostMapping
    public ContenidoAudiovisual crear(@RequestBody ContenidoAudiovisual contenidoAudiovisual) {
        if (contenidoAudiovisual.getIdContenido() != null && contenidoAudiovisual.getIdContenido() == 0L) {
            contenidoAudiovisual.setIdContenido(null);
        }
        return contenidoAudiovisualService.guardar(contenidoAudiovisual);
    }

}
