package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.ListaContenido;
import com.starcritic.dam_proyectspringboot.model.bd.ListaUsuarioId;
import com.starcritic.dam_proyectspringboot.repository.ListaContenidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class ListaContenidoService {

    private final ListaContenidoRepository listaContenidoRepository;

    public ListaContenidoService(ListaContenidoRepository listaContenidoRepository) {
        this.listaContenidoRepository = listaContenidoRepository;
    }

    /**
     * Obtener una entrada de lista_contenido por su clave compuesta.
     * @param id la clave compuesta de la entrada.
     * @return la entrada si existe, en caso contrario un Optional vacio.
     */
    public Optional<ListaContenido> buscarPorId(ListaUsuarioId id) {
        return listaContenidoRepository.findById(id);
    }

    /**
     * Guardar (crear o actualizar) una entrada de contenido en lista de un usuario.
     * @param listaContenido el objeto a persistir.
     * @return el objeto guardado.
     */
    @Transactional
    public ListaContenido guardar(ListaContenido listaContenido) {
        return listaContenidoRepository.save(listaContenido);
    }

    /**
     * Eliminar una entrada de lista_contenido por su clave compuesta.
     * @param id la clave compuesta de la entrada a eliminar.
     */
    @Transactional
    public void eliminarPorId(ListaUsuarioId id) {
        listaContenidoRepository.deleteById(id);
    }

    /**
     * Comprueba si un contenido se encuentra en una lista concreta de un usuario.
     * @param idUsuario el identificador del usuario.
     * @param nombreLista el nombre de la lista.
     * @param idContenido el identificador del contenido.
     * @return true si el contenido pertenece a la lista, false en caso contrario.
     */
    public boolean existeContenidoEnLista(Long idUsuario, String nombreLista, Long idContenido) {
        return listaContenidoRepository
                .contentExistInList(idUsuario, nombreLista, idContenido);
    }

    /**
     * Obtener todos los contenidos de una lista concreta de un usuario.
     * @param idUsuario el identificador del usuario.
     * @param nombreLista el nombre de la lista.
     * @return los contenidos de la lista en formato lista.
     */
    public List<ListaContenido> obtenerContenidoDeLista(Long idUsuario, String nombreLista) {
        return listaContenidoRepository.getUserListContents(idUsuario, nombreLista);
    }

    /**
     * Eliminar un contenido de una lista concreta de un usuario.
     * @param idUsuario el identificador del usuario.
     * @param nombreLista el nombre de la lista.
     * @param idContenido el identificador del contenido.
     * @return el nº de filas afectadas, sera mayor que 0 si la operación fue exitosa.
     */
    @Transactional
    public int eliminarContenidoDeLista(Long idUsuario, String nombreLista, Long idContenido) {
        return listaContenidoRepository.eliminarContenidoDeLista(idUsuario, nombreLista, idContenido);
    }
}
