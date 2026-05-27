package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Jesús Santos Baquero
 */
@Embeddable
public class ContenidoUsuarioId implements Serializable {

    private Long idUsuarioRegistrado;
    private Long idContenido;

    public ContenidoUsuarioId() {}

    public ContenidoUsuarioId(Long idUsuarioRegistrado, Long idContenido) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.idContenido = idContenido;
    }

    public Long getIdUsuarioRegistrado() {
        return idUsuarioRegistrado;
    }

    public void setIdUsuarioRegistrado(Long idUsuarioRegistrado) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
    }

    public Long getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Long idContenido) {
        this.idContenido = idContenido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuarioRegistrado, idContenido);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContenidoUsuarioId other = (ContenidoUsuarioId) obj;
        return Objects.equals(idUsuarioRegistrado, other.idUsuarioRegistrado)
                && Objects.equals(idContenido, other.idContenido);
    }
}
