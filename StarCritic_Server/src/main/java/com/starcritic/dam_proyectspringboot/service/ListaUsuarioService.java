package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.ListaUsuario;
import com.starcritic.dam_proyectspringboot.repository.ListaUsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class ListaUsuarioService {

    private final ListaUsuarioRepository listaUsuarioRepository;

    public ListaUsuarioService(ListaUsuarioRepository listaUsuarioRepository) {
        this.listaUsuarioRepository = listaUsuarioRepository;
    }

    /**
     * Obtener una lista de usuario por su clave compuesta.
     * @param id la clave compuesta de la lista.
     * @return la lista si existe, en caso contrario un Optional vacio.
     */
    public Optional<ListaUsuario> buscarPorId(ListaUsuario.PK id) {
        return listaUsuarioRepository.findById(id);
    }

    /**
     * Guardar (crear o actualizar) una lista de usuario.
     * @param listaUsuario el objeto lista a persistir.
     * @return la lista guardada.
     */
    @Transactional
    public ListaUsuario guardar(ListaUsuario listaUsuario) {
        return listaUsuarioRepository.save(listaUsuario);
    }

    /**
     * Eliminar una lista de usuario por su clave compuesta.
     * @param id la clave compuesta de la lista a eliminar.
     */
    @Transactional
    public void eliminarPorId(ListaUsuario.PK id) {
        listaUsuarioRepository.deleteById(id);
    }

    /**
     * Obtener todas las listas que ha creado un usuario.
     * @param idUsuarioRegistrado el identificador del usuario.
     * @return las listas del usuario en formato lista.
     */
    public List<ListaUsuario> obtenerListasDeUsuario(Long idUsuarioRegistrado) {
        return listaUsuarioRepository.getListasDeUsuario(idUsuarioRegistrado);
    }

    /**
     * Eliminar una lista de un usuario indicando su nombre.
     * @param idUsuarioRegistrado el identificador del usuario propietario.
     * @param nombreLista el nombre de la lista a eliminar.
     */
    @Transactional
    public void eliminarLista(Long idUsuarioRegistrado, String nombreLista) {
        listaUsuarioRepository.deleteLista(idUsuarioRegistrado, nombreLista);
    }
}
