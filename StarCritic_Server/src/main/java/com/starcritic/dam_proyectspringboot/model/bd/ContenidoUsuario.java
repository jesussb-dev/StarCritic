/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "contenido_usuario")
public class ContenidoUsuario {
    @EmbeddedId
    private ContenidoUsuarioId idContenidoUsuario;
    @ManyToOne
    @MapsId("idUsuarioRegistrado")
    @JoinColumn(name = "ID_usuario_registrado")
    private UsuarioRegistrado usuario;
    @ManyToOne
    @MapsId("idContenido")
    @JoinColumn(name = "ID_contenido")
    private Contenido contenido;
    private LocalDate fechaVisita;
    private int numVisitas;

    public ContenidoUsuarioId getIdContenidoUsuario() {
        return idContenidoUsuario;
    }

    public void setIdContenidoUsuario(ContenidoUsuarioId idContenidoUsuario) {
        this.idContenidoUsuario = idContenidoUsuario;
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

    public LocalDate getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(LocalDate fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public int getNumVisitas() {
        return numVisitas;
    }

    public void setNumVisitas(int numVisitas) {
        this.numVisitas = numVisitas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idContenidoUsuario);
        hash = 29 * hash + Objects.hashCode(this.usuario);
        hash = 29 * hash + Objects.hashCode(this.contenido);
        hash = 29 * hash + Objects.hashCode(this.fechaVisita);
        hash = 29 * hash + this.numVisitas;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContenidoUsuario other = (ContenidoUsuario) obj;
        if (this.numVisitas != other.numVisitas) {
            return false;
        }
        if (!Objects.equals(this.idContenidoUsuario, other.idContenidoUsuario)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.contenido, other.contenido)) {
            return false;
        }
        return Objects.equals(this.fechaVisita, other.fechaVisita);
    }
 

}
