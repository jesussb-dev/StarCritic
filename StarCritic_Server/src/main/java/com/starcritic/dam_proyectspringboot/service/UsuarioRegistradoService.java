package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.UsuarioRegistrado;
import com.starcritic.dam_proyectspringboot.repository.UsuarioRegistradoRepository;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class UsuarioRegistradoService {

    private final UsuarioRegistradoRepository usuarioRegistradoRepository;

    public UsuarioRegistradoService(UsuarioRegistradoRepository usuarioRegistradoRepository) {
        this.usuarioRegistradoRepository = usuarioRegistradoRepository;
    }

    /**
     * Obtener todos los usuarios registrados en la base de datos.
     * @return los usuarios en formato lista.
     */
    public List<UsuarioRegistrado> listarTodos() {
        return usuarioRegistradoRepository.findAll();
    }

    /**
     * Obtener un usuario por su identificador propio de la base de datos.
     * @param id el identificador unico en la base de datos.
     * @return el usuario si existe, en caso contrario un Optional vacio.
     */
    public Optional<UsuarioRegistrado> buscarPorId(Long id) {
        return usuarioRegistradoRepository.findById(id);
    }

    /**
     * Crea o actualiza un usuario. La contraseña recibida se trata como texto
     * plano y se cifra con BCrypt. En actualizaciones (id existente) sin
     * contraseña en el cuerpo, se conserva el hash ya almacenado para no
     * borrarlo accidentalmente.
     * @param usuarioRegistrado el objeto usuario a persistir.
     * @return el usuario guardado con su identificador asignado.
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

    /**
     * Eliminar un usuario registrado por su identificador propio de la base de datos.
     * @param id el identificador unico del usuario.
     */
    @Transactional
    public void eliminarPorId(Long id) {
        usuarioRegistradoRepository.deleteById(id);
    }

    /**
     * Verifica credenciales: devuelve el usuario si el nombre existe y la
     * contraseña en texto plano coincide con el hash almacenado.
     * @param nombreUsuario el nombre de usuario.
     * @param contrasenhaPlano la contraseña en texto plano.
     * @return el usuario si las credenciales son validas, en caso contrario un Optional vacio.
     */
    public Optional<UsuarioRegistrado> login(String nombreUsuario, String contrasenhaPlano) {
        return usuarioRegistradoRepository.findByNombreUsuario(nombreUsuario)
                .filter(u -> u.getContrasenha() != null
                        && BCrypt.checkpw(contrasenhaPlano, u.getContrasenha()));
    }

    /**
     * Verifica que la contraseña dada coincide con la almacenada para un usuario.
     * @param idUsuario el identificador del usuario.
     * @param contrasenhaPlano la contraseña en texto plano a verificar.
     * @return true si la contraseña coincide, false en caso contrario o si el usuario no existe.
     */
    public boolean verificarContrasenha(Long idUsuario, String contrasenhaPlano) {
        return usuarioRegistradoRepository.findById(idUsuario)
                .map(u -> u.getContrasenha() != null
                        && BCrypt.checkpw(contrasenhaPlano, u.getContrasenha()))
                .orElse(false);
    }

    /**
     * Cambiar la contraseña de un usuario existente, cifrandola con BCrypt.
     * @param idUsuario el identificador del usuario.
     * @param nuevaContrasenhaPlano la nueva contraseña en texto plano.
     * @return true si el cambio fue exitoso, false si el usuario no existe.
     */
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

    /**
     * Actualiza solo el flag baneado sobre la entidad gestionada. Evita el
     * WrongClassException que ocurre al hacer merge() de un UsuarioRegistrado
     * plano cuando la fila corresponde a un Critico (herencia JOINED).
     * @param idUsuario el identificador del usuario.
     * @param baneado el nuevo valor del flag baneado.
     * @return true si la operación fue exitosa, false si el usuario no existe.
     */
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
