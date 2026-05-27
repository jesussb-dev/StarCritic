package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListaUsuarioId implements Serializable {

    @Column(name = "ID_usuario_registrado")
    private Long idUsuarioRegistrado;
    @Column(name = "nombre_lista")
    private String nombreLista;
    @Column(name = "ID_contenido")
    private Long idContenido;

    public ListaUsuarioId() {}

    public ListaUsuarioId(Long idUsuarioRegistrado, String nombreLista, Long idContenido) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.nombreLista = nombreLista;
        this.idContenido = idContenido;
    }

    public Long getIdUsuarioRegistrado() {
        return idUsuarioRegistrado;
    }

    public void setIdUsuarioRegistrado(Long idUsuarioRegistrado) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public Long getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Long idContenido) {
        this.idContenido = idContenido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuarioRegistrado, nombreLista, idContenido);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ListaUsuarioId other = (ListaUsuarioId) obj;
        return Objects.equals(idUsuarioRegistrado, other.idUsuarioRegistrado)
                && Objects.equals(nombreLista, other.nombreLista)
                && Objects.equals(idContenido, other.idContenido);
    }
}
