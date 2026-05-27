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

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/etiquetas")
public class EtiquetaEditorialController {

    private final EtiquetaEditorialService etiquetaEditorialService;

    public EtiquetaEditorialController(EtiquetaEditorialService etiquetaEditorialService) {
        this.etiquetaEditorialService = etiquetaEditorialService;
    }

    /**
     * Obtener todas las etiquetas editoriales existentes en la base de datos.
     * @return todas las etiquetas en formato lista.
     */
    @GetMapping
    public List<EtiquetaEditorial> listarTodos() {
        return etiquetaEditorialService.listarTodos();
    }

    /**
     * Obtener todas las etiquetas editoriales asignadas a un contenido concreto.
     * @param idContenido el identificador del contenido en la base de datos.
     * @return las etiquetas de ese contenido en formato lista.
     */
    @GetMapping("/contenido/{idContenido}")
    public List<EtiquetaEditorial> deContenido(@PathVariable Long idContenido) {
        return etiquetaEditorialService.obtenerEtiquetasDe(idContenido);
    }

    /**
     * Obtener una etiqueta editorial a través de su identificador en la base de datos.
     * @param id el identificador unico dentro de la base de datos.
     * @return en caso de exito devolvera la etiqueta, en caso de error una
     * respuesta de error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EtiquetaEditorial> porId(@PathVariable Long id) {
        return etiquetaEditorialService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crear una etiqueta editorial nueva en la base de datos.
     * @param etiqueta el objeto etiqueta editorial que se desea insertar.
     * @return la etiqueta guardada si la operación fue exitosa.
     */
    @PostMapping
    public EtiquetaEditorial crear(@RequestBody EtiquetaEditorial etiqueta) {
        return etiquetaEditorialService.guardar(etiqueta);
    }

    /**
     * Eliminar una etiqueta editorial de la base de datos.
     * @param id el identificador unico dentro de la base de datos de la etiqueta
     * a eliminar.
     * @return no devolvera nada en caso de exito, en caso de error la respuesta del error.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        etiquetaEditorialService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Asignar una etiqueta editorial a un contenido en concreto.
     * @param idContenido el identificador del contenido en la base de datos.
     * @param idEtiqueta el identificador de la etiqueta en la base de datos.
     * @return no devolvera nada en caso de exito, en caso de error la respuesta del error.
     */
    @PostMapping("/contenido/{idContenido}/etiqueta/{idEtiqueta}")
    public ResponseEntity<Void> asignar(@PathVariable Long idContenido,
                                        @PathVariable Long idEtiqueta) {
        etiquetaEditorialService.asignarEtiqueta(idContenido, idEtiqueta);
        return ResponseEntity.noContent().build();
    }

    /**
     * Desasignar una etiqueta editorial de un contenido en concreto.
     * @param idContenido el identificador del contenido en la base de datos.
     * @param idEtiqueta el identificador de la etiqueta en la base de datos.
     * @return no devolvera nada en caso de exito, en caso de error la respuesta del error.
     */
    @DeleteMapping("/contenido/{idContenido}/etiqueta/{idEtiqueta}")
    public ResponseEntity<Void> desasignar(@PathVariable Long idContenido,
                                           @PathVariable Long idEtiqueta) {
        etiquetaEditorialService.desasignarEtiqueta(idContenido, idEtiqueta);
        return ResponseEntity.noContent().build();
    }
}
