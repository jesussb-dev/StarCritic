package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.repository.EstadisticasRepository;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Consultas agregadas para los paneles de estadisticas. */
/**
 * @author Jesús Santos Baquero
 */
@Service
@Transactional(readOnly = true)
public class EstadisticasService {

    private final EstadisticasRepository estadisticasRepository;

    public EstadisticasService(EstadisticasRepository estadisticasRepository) {
        this.estadisticasRepository = estadisticasRepository;
    }

    // ===================== PERSONALES ===================== //

    /**
     * Obtener la cantidad de contenidos que un usuario tiene en sus listas,
     * agrupados por tipo de contenido.
     * @param idUsuario el identificador del usuario.
     * @return un mapa con clave el tipo de contenido y valor la cantidad de contenidos.
     */
    public Map<String, Double> contenidoEnListasPorTipo(Long idUsuario) {
        return estadisticasRepository.contenidoEnListasPorTipo(idUsuario);
    }

    /**
     * Obtener la media de puntuaciones por aspecto de las criticas hechas por un usuario.
     * @param idUsuario el identificador del usuario.
     * @return un mapa con clave el nombre del aspecto y valor la media de puntuaciones.
     */
    public Map<String, Double> mediaPorAspectoUsuario(Long idUsuario) {
        return estadisticasRepository.mediaPorAspectoUsuario(idUsuario);
    }

    /**
     * Obtener la cantidad de visitas realizadas por un usuario agrupadas por mes.
     * @param idUsuario el identificador del usuario.
     * @return un mapa con clave el mes (YYYY-MM) y valor el nº de visitas.
     */
    public Map<String, Double> visitasPorMesUsuario(Long idUsuario) {
        return estadisticasRepository.visitasPorMesUsuario(idUsuario);
    }

    // ===================== GENERALES ===================== //

    /**
     * Obtener los contenidos más visitados de la aplicación junto a su nº de visitas.
     * @param limite el nº máximo de contenidos a devolver.
     * @return un mapa con clave el título del contenido y valor el nº de visitas.
     */
    public Map<String, Double> topContenidoMasVisitado(int limite) {
        return estadisticasRepository.topContenidoMasVisitado(limite);
    }


    /**
     * Obtener la cantidad de contenidos guardados en la base de datos agrupados por su origen.
     * @return un mapa con clave el origen y valor el nº de contenidos.
     */
    public Map<String, Double> catalogoPorOrigen() {
        return estadisticasRepository.catalogoPorOrigen();
    }



    /**
     * Obtener la distribución de puntuaciones de las críticas dividida en cinco grupos.
     * @return un mapa con clave el rango de puntuación y valor el nº de críticas.
     */
    public Map<String, Double> distribucionPuntuaciones() {
        return estadisticasRepository.distribucionPuntuaciones();
    }


}
