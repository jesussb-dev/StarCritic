package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.EtiquetaEditorial;
import com.starcritic.dam_proyectspringboot.repository.EtiquetaEditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EtiquetaEditorialService {

    private final EtiquetaEditorialRepository etiquetaEditorialRepository;

    public EtiquetaEditorialService(EtiquetaEditorialRepository etiquetaEditorialRepository) {
        this.etiquetaEditorialRepository = etiquetaEditorialRepository;
    }

    public List<EtiquetaEditorial> listarTodos() {
        return etiquetaEditorialRepository.findAllByOrderByNombreAsc();
    }

    public Optional<EtiquetaEditorial> buscarPorId(Long id) {
        return etiquetaEditorialRepository.findById(id);
    }

    @Transactional
    public EtiquetaEditorial guardar(EtiquetaEditorial etiquetaEditorial) {
        return etiquetaEditorialRepository.save(etiquetaEditorial);
    }

    @Transactional
    public void eliminarPorId(Long id) {
        etiquetaEditorialRepository.deleteById(id);
    }

    public List<EtiquetaEditorial> obtenerEtiquetasDe(Long idContenido) {
        return etiquetaEditorialRepository.obtenerEtiquetasDe(idContenido);
    }

    @Transactional
    public int asignarEtiqueta(Long idContenido, Long idEtiqueta) {
        return etiquetaEditorialRepository.asignarEtiqueta(idContenido, idEtiqueta);
    }

    @Transactional
    public int desasignarEtiqueta(Long idContenido, Long idEtiqueta) {
        return etiquetaEditorialRepository.desasignarEtiqueta(idContenido, idEtiqueta);
    }
}
