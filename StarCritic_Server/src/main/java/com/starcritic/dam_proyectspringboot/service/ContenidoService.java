package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.Contenido;
import com.starcritic.dam_proyectspringboot.repository.ContenidoRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class ContenidoService {

    private final ContenidoRepository contenidoRepository;

    public ContenidoService(ContenidoRepository contenidoRepository) {
        this.contenidoRepository = contenidoRepository;
    }

    /**
     * Obtener un contenido por su identificador propio de la base de datos.
     * @param id el identificador unico en la base de datos.
     * @return el contenido si existe, en caso contrario un Optional vacio.
     */
    public Optional<Contenido> buscarPorId(Long id) {
        return contenidoRepository.findById(id);
    }

    /**
     * Eliminar un contenido por su identificador propio de la base de datos.
     * @param id el identificador unico en la base de datos.
     */
    @Transactional
    public void eliminarPorId(Long id) {
        contenidoRepository.deleteById(id);
    }

    /**
     * Calcular la media de puntuaciones de un aspecto de un contenido audiovisual.
     * @param idContenido el identificador del contenido audiovisual.
     * @param idAspecto el identificador del aspecto del que se calculara la media.
     * @return la media de puntuaciones del aspecto.
     */
    public double mediaAspectoAudiovisual(Long idContenido, Long idAspecto) {
        return contenidoRepository.mediaAspectoAudiovisual(idContenido, idAspecto);
    }

    /**
     * Calcular la media de puntuaciones de un aspecto de un videojuego.
     * @param idVideojuego el identificador del videojuego.
     * @param idAspecto el identificador del aspecto del que se calculara la media.
     * @return la media de puntuaciones del aspecto.
     */
    public double mediaAspectoVideojuego(Long idVideojuego, Long idAspecto) {
        return contenidoRepository.mediaAspectoVideojuego(idVideojuego, idAspecto);
    }

    /**
     * Modificar si un contenido se encuentra oculto o no.
     * @param idContenido el contenido sobre el que se quiere hacer la modificación.
     * @param oculto el nuevo valor tras la modificación.
     * @return el nº de filas afectadas, sera mayor que 0 si la operación fue exitosa.
     */
    @Transactional
    public int actualizarOculto(Long idContenido, boolean oculto) {
        return contenidoRepository.actualizarOculto(idContenido, oculto);
    }

    /**
     * Modificar si un contenido se encuentra destacado o no.
     * @param idContenido el contenido sobre el que se quiere hacer la modificación.
     * @param destacado el nuevo valor tras la modificación.
     * @return el nº de filas afectadas, sera mayor que 0 si la operación fue exitosa.
     */
    @Transactional
    public int actualizarDestacado(Long idContenido, boolean destacado) {
        return contenidoRepository.actualizarDestacado(idContenido, destacado);
    }
}
