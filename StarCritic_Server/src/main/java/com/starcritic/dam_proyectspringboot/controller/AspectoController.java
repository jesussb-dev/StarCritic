package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Aspecto;
import com.starcritic.dam_proyectspringboot.service.AspectoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/aspectos")
public class AspectoController {

    private final AspectoService aspectoService;

    public AspectoController(AspectoService aspectoService) {
        this.aspectoService = aspectoService;
    }
    
    /**
     * Obtener todos los aspectos existentes en la base de datos.
     * @return todos los aspectos en formato lista.
     */
    @GetMapping
    public List<Aspecto> listarTodos() {
        return aspectoService.listarTodos();
    }
    /**
     * Obtener todos los aspectos que pueda tener un contenido audiovisual.
     * @return todos los aspectos de un contenido audiovisual en formato lista.
     */
    @GetMapping("/audiovisual")
    public List<Aspecto> audiovisual() {
        return aspectoService.obtenerAspectosAudiovisual();
    }

    /**
     * Obtener todos los aspectos que pueda tener un contenido videojuego.
     * @return todos los aspectos de un contenido videojuego en formato lista.
     */
    @GetMapping("/videojuego")
    public List<Aspecto> videojuego() {
        return aspectoService.obtenerAspectosVideojuego();
    }
    
    
    /**
     * ResponseEntity representa la respuesta HTTP completa: código de estado,
     * cabeceras y cuerpo. Permite controlar explícitamente qué se le devuelve
     * al cliente, por ejemplo respondiendo 200 con el objeto encontrado o 404
     * cuando la búsqueda no arroja resultados. Esto me permite detectar en e cliente
     * cuando fallo la busqueda.
     */ 
    
    /**
     * Obtener un aspecto a traves de si identificador dentro de la base de datos
     * @param id el identificador uno dentro de la base de datos.
     * @return en caso de exito devolvera el aspecto en el formato indicado, en caso 
     * de error enviara una respuesta de error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aspecto> porId(@PathVariable Long id) {
        return aspectoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
