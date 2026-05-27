package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.ListaContenido;
import com.starcritic.dam_proyectspringboot.model.bd.ListaUsuarioId;
import com.starcritic.dam_proyectspringboot.repository.ListaContenidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListaContenidoService {

    private final ListaContenidoRepository listaContenidoRepository;

    public ListaContenidoService(ListaContenidoRepository listaContenidoRepository) {
        this.listaContenidoRepository = listaContenidoRepository;
    }

    public Optional<ListaContenido> buscarPorId(ListaUsuarioId id) {
        return listaContenidoRepository.findById(id);
    }

    @Transactional
    public ListaContenido guardar(ListaContenido listaContenido) {
        return listaContenidoRepository.save(listaContenido);
    }

    @Transactional
    public void eliminarPorId(ListaUsuarioId id) {
        listaContenidoRepository.deleteById(id);
    }

    public boolean existeContenidoEnLista(Long idUsuario, String nombreLista, Long idContenido) {
        return listaContenidoRepository
                .contentExistInList(idUsuario, nombreLista, idContenido);
    }

    public List<ListaContenido> obtenerContenidoDeLista(Long idUsuario, String nombreLista) {
        return listaContenidoRepository.getUserListContents(idUsuario, nombreLista);
    }

    @Transactional
    public int eliminarContenidoDeLista(Long idUsuario, String nombreLista, Long idContenido) {
        return listaContenidoRepository.eliminarContenidoDeLista(idUsuario, nombreLista, idContenido);
    }
}
