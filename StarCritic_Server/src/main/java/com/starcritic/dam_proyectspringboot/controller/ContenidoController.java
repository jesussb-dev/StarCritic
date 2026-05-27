package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Contenido;
import com.starcritic.dam_proyectspringboot.service.ContenidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/contenidos")
public class ContenidoController {

    private final ContenidoService contenidoService;

    public ContenidoController(ContenidoService contenidoService) {
        this.contenidoService = contenidoService;
    }
    

    
    /**
     * Obtener un contenido por su id propio de la BD
     * @param id el id de la BD
     * @return el objeto guardado en la BD con su respectivo formato
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contenido> porId(@PathVariable Long id) {
        return contenidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un contenido de la BD
     * @param id el ide propio de la BD del contenido a eliminar
     * @return  no devuelve nada, solo cuando la operación falle
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contenidoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtener la media de un aspecto de un contenido audiovisual
     * @param idContenido el identificador del contenido audiovisual al que pertenece el aspecto
     * @param aspecto el identificador del aspecto del que se calculara la media
     * @return la media del aspecto
     */
    @GetMapping("/{id}/media-audiovisual")
    public double mediaAudiovisual(@PathVariable("id") Long idContenido,
                                   @RequestParam Long aspecto) {
        return contenidoService.mediaAspectoAudiovisual(idContenido, aspecto);
    }

    /**
     * Obtener la media de un aspecto de un contenido videojuego
     * @param idVideojuego el identificador del contenido videouego al que pertenece el aspecto
     * @param aspecto l identificador del aspecto del que se calculara la media
     * @return la media del aspecto
     */
    @GetMapping("/{id}/media-videojuego")
    public double mediaVideojuego(@PathVariable("id") Long idVideojuego,
                                  @RequestParam Long aspecto) {
        return contenidoService.mediaAspectoVideojuego(idVideojuego, aspecto);
    }
    
    /**
     * Actualizar el valor de oculto de un contenido
     * @param idContenido el identificador del contenido al que pertenece el aspecto
     * @param valor el valor que se le dara al parametro oculto
     * @return no devolvera nada pero si falla devolvera el error
     */
    @PatchMapping("/{id}/oculto")
    public ResponseEntity<Void> actualizarOculto(@PathVariable("id") Long idContenido,
                                                 @RequestParam boolean valor) {
        int filas = contenidoService.actualizarOculto(idContenido, valor);
        return filas > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Actualizar el valor de destacado de un contenido
     * @param idContenido el identificador del contenido al que pertenece el aspecto
     * @param valor el valor que se le dara al parametro destacado
     * @return no devolvera nada pero si falla devolvera el error
     */
    @PatchMapping("/{id}/destacado")
    public ResponseEntity<Void> actualizarDestacado(@PathVariable("id") Long idContenido,
                                                    @RequestParam boolean valor) {
        int filas = contenidoService.actualizarDestacado(idContenido, valor);
        return filas > 0 ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
