package com.starcritic.dam_proyectspringboot.service;

import com.starcritic.dam_proyectspringboot.model.bd.Critico;
import com.starcritic.dam_proyectspringboot.model.bd.EstadoCertificacion;
import com.starcritic.dam_proyectspringboot.repository.CriticoRepository;
import com.starcritic.dam_proyectspringboot.repository.UsuarioRegistradoRepository;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriticoService {

    private final CriticoRepository criticoRepository;
    private final UsuarioRegistradoRepository usuarioRegistradoRepository;

    public CriticoService(CriticoRepository criticoRepository,
                          UsuarioRegistradoRepository usuarioRegistradoRepository) {
        this.criticoRepository = criticoRepository;
        this.usuarioRegistradoRepository = usuarioRegistradoRepository;
    }

    public List<Critico> listarTodos() {
        return criticoRepository.findAll();
    }

    public Optional<Critico> buscarPorId(Long id) {
        return criticoRepository.findById(id);
    }

    /**
     * Comprueba si un usuario_registrado es ademas critico. Con herencia JOINED
     * basta con verificar que existe la fila de la subclase con ese mismo id.
     */
    public boolean esCritico(Long idUsuario) {
        return idUsuario != null && criticoRepository.existsById(idUsuario);
    }

    /**
     * Crea o actualiza un crítico (herencia JOINED sobre usuario_registrado).
     * La contraseña recibida se trata como texto plano y se cifra; si llega en
     * blanco se conserva el hash del usuario ya existente para no borrarlo al
     * promover/actualizar un crítico.
     */
    @Transactional
    public Critico guardar(Critico critico) {
        String contrasenha = critico.getContrasenha();
        boolean tieneContrasenha = contrasenha != null && !contrasenha.isBlank();

        if (critico.getIdUsuario() != null) {
            // Cargamos la entidad gestionada para evitar StaleObjectStateException:
            // hacer merge() de una entidad detached con el mismo id que ya está en la
            // caché L1 de la sesión JPA causa conflicto en herencia JOINED.
            return criticoRepository.findById(critico.getIdUsuario())
                    .map(existente -> {
                        existente.setCertificacion(critico.getCertificacion());
                        existente.setEstado(critico.getEstado());
                        existente.setNombreUsuario(critico.getNombreUsuario());
                        existente.setCorreoElectronico(critico.getCorreoElectronico());
                        existente.setNombre(critico.getNombre());
                        existente.setApellido1(critico.getApellido1());
                        existente.setApellido2(critico.getApellido2());
                        existente.setRol(critico.getRol());
                        existente.setBaneado(critico.isBaneado());
                        existente.setFechaCreacion(critico.getFechaCreacion());
                        if (tieneContrasenha) {
                            existente.setContrasenha(BCrypt.hashpw(contrasenha, BCrypt.gensalt(13)));
                        }
                        return existente;
                    })
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(
                            "Crítico no encontrado con id: " + critico.getIdUsuario()));
        }

        if (tieneContrasenha) {
            critico.setContrasenha(BCrypt.hashpw(contrasenha, BCrypt.gensalt(13)));
        }
        return criticoRepository.save(critico);
    }

    /**
     * Promueve un usuario_registrado existente a critico anhadiendo solo la fila
     * de la subclase (herencia JOINED). Si el usuario ya es critico, se devuelve
     * tal cual sin reinsertar.
     */
    @Transactional
    public Critico promover(Long idUsuario, String certificacion, EstadoCertificacion estado) {
        if (usuarioRegistradoRepository.findById(idUsuario).isEmpty()) {
            throw new jakarta.persistence.EntityNotFoundException(
                    "Usuario no encontrado con id: " + idUsuario);
        }
        Optional<Critico> yaCritico = criticoRepository.findById(idUsuario);
        if (yaCritico.isPresent()) {
            return yaCritico.get();
        }
        EstadoCertificacion estadoFinal = estado != null ? estado : EstadoCertificacion.NO_SOLICITADA;
        String certificacionFinal = certificacion != null ? certificacion : "";
        criticoRepository.insertarCritico(idUsuario, certificacionFinal, estadoFinal.getDbValue());
        return criticoRepository.findById(idUsuario)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(
                        "No se pudo promover el usuario con id: " + idUsuario));
    }

    public List<Critico> buscarPorEstado(EstadoCertificacion estado) {
        return criticoRepository.findByEstado(estado);
    }

    public List<Critico> obtenerCertificacionesPendientes() {
        return criticoRepository.obtenerCertificacionesPendientes();
    }
}
