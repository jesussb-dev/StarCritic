package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.service.EstadisticasService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    public EstadisticasController(EstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    // ===================== PERSONALES ===================== //

    /**
     * Obtener la cantidad de contenidos que se posee en listas diferenciandolos
     * por su tipo.
     * @param idUsuario el identificador unico del usuario en la base de datos
     * @return un mapa con clave el tipo de contenido y valor la cantidad de contenidos
     */
    @GetMapping("/usuario/{idUsuario}/contenido-en-listas")
    public Map<String, Double> contenidoEnListas(@PathVariable Long idUsuario) {
        return estadisticasService.contenidoEnListasPorTipo(idUsuario);
    }

    /**
     * Obtener la media de puntuaciones que un usuario ha otorgado en cada aspecto,
     * teniendo en cuenta tanto críticas a contenidos audiovisuales como a videojuegos.
     * @param idUsuario el identificador unico del usuario en la base de datos
     * @return un mapa con clave el nombre del aspecto y valor la media de puntuaciones
     */
    @GetMapping("/usuario/{idUsuario}/media-por-aspecto")
    public Map<String, Double> mediaPorAspecto(@PathVariable Long idUsuario) {
        return estadisticasService.mediaPorAspectoUsuario(idUsuario);
    }

    /**
     * Obtener la cantidad de visitas que un usuario ha realizado agrupadas por mes.
     * @param idUsuario el identificador unico del usuario en la base de datos
     * @return un mapa con clave el mes (formato YYYY-MM) y valor el nº de visitas
     */
    @GetMapping("/usuario/{idUsuario}/visitas-por-mes")
    public Map<String, Double> visitasPorMes(@PathVariable Long idUsuario) {
        return estadisticasService.visitasPorMesUsuario(idUsuario);
    }


    // ===================== GENERALES ===================== //

    /**
     * Obtener los contenidos más visitados de la aplicación junto con su nº de visitas.
     * @param limite el nº máximo de contenidos a devolver (por defecto 10)
     * @return un mapa con clave el título del contenido y valor el nº de visitas
     */
    @GetMapping("/top-visitado")
    public Map<String, Double> topVisitado(@RequestParam(defaultValue = "10") int limite) {
        return estadisticasService.topContenidoMasVisitado(limite);
    }



    /**
     * Obtener la cantidad de contenidos guardados en la base de datos diferenciandolos
     * por su origen (Local, OMDb, RAWG, etc).
     * @return un mapa con clave el origen y valor el nº de contenidos
     */
    @GetMapping("/catalogo-por-origen")
    public Map<String, Double> catalogoPorOrigen() {
        return estadisticasService.catalogoPorOrigen();
    }



    /**
     * Obtener la distribución de las puntuaciones de todas las críticas, dividida
     * en cinco grupos según su rango (0-19, 20-39, 40-59, 60-79, 80-100).
     * @return un mapa con clave el rango de puntuación y valor el nº de críticas
     */
    @GetMapping("/distribucion-puntuaciones")
    public Map<String, Double> distribucionPuntuaciones() {
        return estadisticasService.distribucionPuntuaciones();
    }

}
