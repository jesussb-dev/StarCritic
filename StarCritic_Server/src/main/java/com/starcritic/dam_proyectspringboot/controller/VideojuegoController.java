package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Videojuego;
import com.starcritic.dam_proyectspringboot.service.VideojuegoService;
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
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    /**
     * Obtiene todos los videojuegos guardados en la base de datos.
     * @return la lista de videojuegos.
     */
    @GetMapping
    public List<Videojuego> listarTodos() {
        return videojuegoService.listarTodos();
    }

    /**
     * Obtener un videojuego por su id propio de la base de datos.
     * @param id el identificador unico en la base de datos.
     * @return el objeto guardado en la base de datos con su respectivo formato.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> porId(@PathVariable Long id) {
        return videojuegoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Obtener el videojuego a traves del id guardado de la API externa RAWG.
     * @param idRawg el id del videojuego en la API externa RAWG.
     * @return el videojuego guardado en la base de datos en caso de exito, 404 si no existe.
     */
    @GetMapping("/rawg/{idRawg}")
    public ResponseEntity<Videojuego> porIdRawg(@PathVariable int idRawg) {
        return videojuegoService.buscarPorIdRawg(idRawg)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Comprueba si en la base de datos existe un videojuego guardado con ese id de
     * la API externa RAWG.
     * @param idRawg el id del videojuego en la API externa RAWG.
     * @return true si existe el videojuego en la base de datos, false en caso contrario.
     */
    @GetMapping("/rawg/{idRawg}/existe")
    public boolean existeRawg(@PathVariable int idRawg) {
        return videojuegoService.existePorIdRawg(idRawg);
    }

    /**
     * Crea un videojuego o lo "guarda" en la base de datos, ya que si no es Local solo serán
     * los datos que se mostraran en la aplicación.
     * @param videojuego los datos del videojuego en el formato solicitado.
     * @return el videojuego guardado si la operación fue exitosa.
     */
    @PostMapping
    public Videojuego crear(@RequestBody Videojuego videojuego) {
        if (videojuego.getIdContenido() != null && videojuego.getIdContenido() == 0L) {
            videojuego.setIdContenido(null);
        }
        return videojuegoService.guardar(videojuego);
    }
}
