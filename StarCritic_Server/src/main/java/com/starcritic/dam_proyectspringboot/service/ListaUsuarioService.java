package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.ListaUsuario;
import com.starcritic.dam_proyectspringboot.repository.ListaUsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListaUsuarioService {

    private final ListaUsuarioRepository listaUsuarioRepository;

    public ListaUsuarioService(ListaUsuarioRepository listaUsuarioRepository) {
        this.listaUsuarioRepository = listaUsuarioRepository;
    }

    public Optional<ListaUsuario> buscarPorId(ListaUsuario.PK id) {
        return listaUsuarioRepository.findById(id);
    }

    @Transactional
    public ListaUsuario guardar(ListaUsuario listaUsuario) {
        return listaUsuarioRepository.save(listaUsuario);
    }

    @Transactional
    public void eliminarPorId(ListaUsuario.PK id) {
        listaUsuarioRepository.deleteById(id);
    }

    public List<ListaUsuario> obtenerListasDeUsuario(Long idUsuarioRegistrado) {
        return listaUsuarioRepository.getListasDeUsuario(idUsuarioRegistrado);
    }

    @Transactional
    public void eliminarLista(Long idUsuarioRegistrado, String nombreLista) {
        listaUsuarioRepository.deleteLista(idUsuarioRegistrado, nombreLista);
    }
}
