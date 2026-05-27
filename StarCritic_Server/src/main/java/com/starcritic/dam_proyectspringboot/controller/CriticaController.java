package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Critica;
import com.starcritic.dam_proyectspringboot.model.bd.CriticaAudiovisual;
import com.starcritic.dam_proyectspringboot.model.bd.CriticaVideojuego;
import com.starcritic.dam_proyectspringboot.service.CriticaService;
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
@RequestMapping("/api/criticas")
public class CriticaController {

    private final CriticaService criticaService;

    public CriticaController(CriticaService criticaService) {
        this.criticaService = criticaService;
    }
    
    /**
     * Eliminar una critica de la base de datos.
     * @param id el identificador dentro de la base de datos de la critica
     * que se desea eliminar.
     * @return devolvera el error pero si la operación es exitosa no delvolvera nada,
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        criticaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Obtener todas las criticas de un aspecto en concreto de un contenido audiovisual.
     * @param idAspecto el identificador unico del aspecto en la base de datos.
     * @param idContenido el identificador unico del contenido audiovisual en la base de datos.
     * @return todas las críticas en formato lista.
     */
    @GetMapping("/audiovisual/aspecto/{idAspecto}/contenido/{idContenido}")
    public List<CriticaAudiovisual> audiovisualPorAspecto(@PathVariable Long idAspecto,@PathVariable Long idContenido) {
        return criticaService.obtenerCriticasAudiovisualPorAspecto(idAspecto, idContenido);
    }
    
    /**
     * Obtener todas las criticas de un aspecto en concreto de un contenido videojuego.
     * @param idAspecto el identificador unico del aspecto en la base de datos.
     * @param idVideojuego el identificador unico del contenido videojuego en la base de datos.
     * @return todas las críticas en formato lista.
     */
    @GetMapping("/videojuego/aspecto/{idAspecto}/videojuego/{idVideojuego}")
    public List<CriticaVideojuego> videojuegoPorAspecto(@PathVariable Long idAspecto,@PathVariable Long idVideojuego) {
        return criticaService.obtenerCriticasVideojuegoPorAspecto(idAspecto, idVideojuego);
    }
    
    /**
     * Obtener todas las criticas que un usuario ha realizado a un aspecto a todos los 
     * contenidos audiovisuales.
     * @param idAspecto el identificador unico del aspecto en la base de datos.
     * @param idUsuario el identificador unico del usuario en la base de datos.
     * @return todas las críticas en formato lista.
     */
    @GetMapping("/audiovisual/aspecto/{idAspecto}/usuario/{idUsuario}")
    public List<CriticaAudiovisual> audiovisualPorUsuario(@PathVariable Long idAspecto,@PathVariable Long idUsuario) {
        return criticaService.obtenerCriticasAudiovisualPorUsuario(idAspecto, idUsuario);
    }
    
    /**
     * Obtener todas las criticas que un usuario ha realizado a un aspecto a todos
     * los contenidos videojuegos.
     * @param idAspecto el identificador unico del aspecto en la base de datos.
     * @param idUsuario el identificador unico del usuario en la base de datos.
     * @return todas las críticas en formato lista.
     */
    @GetMapping("/videojuego/aspecto/{idAspecto}/usuario/{idUsuario}")
    public List<CriticaVideojuego> videojuegoPorUsuario(@PathVariable Long idAspecto,@PathVariable Long idUsuario) {
        return criticaService.obtenerCriticasVideojuegoPorUsuario(idAspecto, idUsuario);
    }
    
    /**
     * Comprueba si una crítica le pertenece a un usuario en concreto.
     * @param idCritica el identificador unico de la crítca en la base de datos.
     * @param idUsuario el identificador unico del usuario en la base de datos.
     * @return si al usuario le pertenece esa crítica o no en booleano.
     */
    @GetMapping("/{idCritica}/es-de-usuario/{idUsuario}")
    public boolean esCriticaUsuario(@PathVariable Long idCritica,@PathVariable Long idUsuario) {
        return criticaService.esCriticaUsuario(idUsuario, idCritica);
    }
}
