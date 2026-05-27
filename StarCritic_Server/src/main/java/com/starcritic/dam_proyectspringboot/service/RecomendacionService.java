package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.TipoContenido;
import com.starcritic.dam_proyectspringboot.repository.RecomendacionRepository;
import com.starcritic.dam_proyectspringboot.repository.projection.RecommendedItemView;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Recomendaciones por comportamiento (popularidad global o personalizada). */
@Service
@Transactional(readOnly = true)
public class RecomendacionService {

    private final RecomendacionRepository recomendacionRepository;

    public RecomendacionService(RecomendacionRepository recomendacionRepository) {
        this.recomendacionRepository = recomendacionRepository;
    }

    /**
     * Devuelve hasta {@code limite} recomendaciones del tipo indicado.
     * Si {@code idUsuario} es null o negativo usa popularidad global.
     */
    public List<RecommendedItemView> obtenerRecomendaciones(TipoContenido tipo, Long idUsuario, int limite) {
        return recomendacionRepository.obtenerRecomendaciones(tipo, idUsuario, limite);
    }
}
