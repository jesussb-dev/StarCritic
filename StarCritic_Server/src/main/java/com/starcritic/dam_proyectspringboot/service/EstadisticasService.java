package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.repository.EstadisticasRepository;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Consultas agregadas para los paneles de estadisticas. */
@Service
@Transactional(readOnly = true)
public class EstadisticasService {

    private final EstadisticasRepository estadisticasRepository;

    public EstadisticasService(EstadisticasRepository estadisticasRepository) {
        this.estadisticasRepository = estadisticasRepository;
    }

    // ===================== PERSONALES ===================== //

    public Map<String, Double> contenidoEnListasPorTipo(Long idUsuario) {
        return estadisticasRepository.contenidoEnListasPorTipo(idUsuario);
    }

    public Map<String, Double> mediaPorAspectoUsuario(Long idUsuario) {
        return estadisticasRepository.mediaPorAspectoUsuario(idUsuario);
    }

    public Map<String, Double> visitasPorMesUsuario(Long idUsuario) {
        return estadisticasRepository.visitasPorMesUsuario(idUsuario);
    }

    // ===================== GENERALES ===================== //

    public Map<String, Double> topContenidoMasVisitado(int limite) {
        return estadisticasRepository.topContenidoMasVisitado(limite);
    }


    public Map<String, Double> catalogoPorOrigen() {
        return estadisticasRepository.catalogoPorOrigen();
    }



    public Map<String, Double> distribucionPuntuaciones() {
        return estadisticasRepository.distribucionPuntuaciones();
    }


}
