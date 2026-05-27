package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.UsuarioRegistrado;
import com.starcritic.dam_proyectspringboot.repository.UsuarioRegistradoRepository;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioRegistradoService {

    private final UsuarioRegistradoRepository usuarioRegistradoRepository;

    public UsuarioRegistradoService(UsuarioRegistradoRepository usuarioRegistradoRepository) {
        this.usuarioRegistradoRepository = usuarioRegistradoRepository;
    }

    public List<UsuarioRegistrado> listarTodos() {
        return usuarioRegistradoRepository.findAll();
    }

    public Optional<UsuarioRegistrado> buscarPorId(Long id) {
        return usuarioRegistradoRepository.findById(id);
    }

    /**
     * Crea o actualiza un usuario. La contraseña recibida se trata como texto
     * plano y se cifra con BCrypt. En actualizaciones (id existente) sin
     * contraseña en el cuerpo, se conserva el hash ya almacenado para no
     * borrarlo accidentalmente.
     */
    @Transactional
    public UsuarioRegistrado guardar(UsuarioRegistrado usuarioRegistrado) {
        // El cliente envía idUsuario = 0 (int primitivo) al crear; se normaliza a
        // null para que Spring Data haga INSERT (persist) en lugar de UPDATE (merge).
        if (usuarioRegistrado.getIdUsuario() != null && usuarioRegistrado.getIdUsuario() == 0L) {
            usuarioRegistrado.setIdUsuario(null);
        }

        String contrasenha = usuarioRegistrado.getContrasenha();
        boolean tieneContrasenha = contrasenha != null && !contrasenha.isBlank();

        if (tieneContrasenha) {
            usuarioRegistrado.setContrasenha(BCrypt.hashpw(contrasenha, BCrypt.gensalt(13)));
        } else if (usuarioRegistrado.getIdUsuario() != null) {
            usuarioRegistradoRepository.findById(usuarioRegistrado.getIdUsuario())
                    .ifPresent(existente -> usuarioRegistrado.setContrasenha(existente.getContrasenha()));
        }
        return usuarioRegistradoRepository.save(usuarioRegistrado);
    }

    @Transactional
    public void eliminarPorId(Long id) {
        usuarioRegistradoRepository.deleteById(id);
    }

    /**
     * Verifica credenciales: devuelve el usuario si el nombre existe y la
     * contraseña en texto plano coincide con el hash almacenado.
     */
    public Optional<UsuarioRegistrado> login(String nombreUsuario, String contrasenhaPlano) {
        return usuarioRegistradoRepository.findByNombreUsuario(nombreUsuario)
                .filter(u -> u.getContrasenha() != null
                        && BCrypt.checkpw(contrasenhaPlano, u.getContrasenha()));
    }

    public boolean verificarContrasenha(Long idUsuario, String contrasenhaPlano) {
        return usuarioRegistradoRepository.findById(idUsuario)
                .map(u -> u.getContrasenha() != null
                        && BCrypt.checkpw(contrasenhaPlano, u.getContrasenha()))
                .orElse(false);
    }

    @Transactional
    public boolean cambiarContrasenha(Long idUsuario, String nuevaContrasenhaPlano) {
        return usuarioRegistradoRepository.findById(idUsuario)
                .map(u -> {
                    u.setContrasenha(BCrypt.hashpw(nuevaContrasenhaPlano, BCrypt.gensalt(13)));
                    usuarioRegistradoRepository.save(u);
                    return true;
                })
                .orElse(false);
    }

    // Actualiza solo el flag baneado sobre la entidad gestionada. Evita el
    // WrongClassException que ocurre al hacer merge() de un UsuarioRegistrado
    // plano cuando la fila corresponde a un Critico (herencia JOINED).
    @Transactional
    public boolean setBaneado(Long idUsuario, boolean baneado) {
        return usuarioRegistradoRepository.findById(idUsuario)
                .map(u -> {
                    u.setBaneado(baneado);
                    return true;
                })
                .orElse(false);
    }
}
