package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.EtiquetaEditorial;
import com.starcritic.dam_proyectspringboot.repository.EtiquetaEditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class EtiquetaEditorialService {

    private final EtiquetaEditorialRepository etiquetaEditorialRepository;

    public EtiquetaEditorialService(EtiquetaEditorialRepository etiquetaEditorialRepository) {
        this.etiquetaEditorialRepository = etiquetaEditorialRepository;
    }

    /**
     * Obtener todas las etiquetas editoriales ordenadas alfabeticamente por nombre.
     * @return las etiquetas en formato lista.
     */
    public List<EtiquetaEditorial> listarTodos() {
        return etiquetaEditorialRepository.findAllByOrderByNombreAsc();
    }

    /**
     * Obtener una etiqueta editorial por su identificador propio de la base de datos.
     * @param id el identificador unico en la base de datos.
     * @return la etiqueta si existe, en caso contrario un Optional vacio.
     */
    public Optional<EtiquetaEditorial> buscarPorId(Long id) {
        return etiquetaEditorialRepository.findById(id);
    }

    /**
     * Guardar (crear o actualizar) una etiqueta editorial.
     * @param etiquetaEditorial el objeto etiqueta a persistir.
     * @return la etiqueta guardada con su identificador asignado.
     */
    @Transactional
    public EtiquetaEditorial guardar(EtiquetaEditorial etiquetaEditorial) {
        return etiquetaEditorialRepository.save(etiquetaEditorial);
    }

    /**
     * Eliminar una etiqueta editorial por su identificador propio de la base de datos.
     * @param id el identificador unico de la etiqueta.
     */
    @Transactional
    public void eliminarPorId(Long id) {
        etiquetaEditorialRepository.deleteById(id);
    }

    /**
     * Obtener todas las etiquetas asignadas a un contenido concreto.
     * @param idContenido el identificador del contenido.
     * @return las etiquetas asignadas en formato lista.
     */
    public List<EtiquetaEditorial> obtenerEtiquetasDe(Long idContenido) {
        return etiquetaEditorialRepository.obtenerEtiquetasDe(idContenido);
    }

    /**
     * Asignar una etiqueta a un contenido.
     * @param idContenido el contenido al que asignarle la etiqueta.
     * @param idEtiqueta la etiqueta a asignar.
     * @return el nº de filas afectadas, sera mayor que 0 si la operación fue exitosa.
     */
    @Transactional
    public int asignarEtiqueta(Long idContenido, Long idEtiqueta) {
        return etiquetaEditorialRepository.asignarEtiqueta(idContenido, idEtiqueta);
    }

    /**
     * Desasignar una etiqueta de un contenido.
     * @param idContenido el contenido del que desasignar la etiqueta.
     * @param idEtiqueta la etiqueta a desasignar.
     * @return el nº de filas afectadas, sera mayor que 0 si la operación fue exitosa.
     */
    @Transactional
    public int desasignarEtiqueta(Long idContenido, Long idEtiqueta) {
        return etiquetaEditorialRepository.desasignarEtiqueta(idContenido, idEtiqueta);
    }
}
