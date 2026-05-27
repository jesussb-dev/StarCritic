package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "lista_contenido")
public class ListaContenido {
    @EmbeddedId
    private ListaUsuarioId id;

    @ManyToOne
    @MapsId("idUsuarioRegistrado")
    @JoinColumn(name = "ID_usuario_registrado")
    private UsuarioRegistrado usuario;

    @ManyToOne
    @MapsId("idContenido")
    @JoinColumn(name = "ID_contenido")
    private Contenido contenido;

    public ListaUsuarioId getId() {
        return id;
    }

    public void setId(ListaUsuarioId id) {
        this.id = id;
    }

    public UsuarioRegistrado getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioRegistrado usuario) {
        this.usuario = usuario;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }

    public String getNombreLista() {
        return id != null ? id.getNombreLista() : null;
    }

    public void setNombreLista(String nombreLista) {
        if (id == null) id = new ListaUsuarioId();
        id.setNombreLista(nombreLista);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListaContenido)) return false;
        ListaContenido that = (ListaContenido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
