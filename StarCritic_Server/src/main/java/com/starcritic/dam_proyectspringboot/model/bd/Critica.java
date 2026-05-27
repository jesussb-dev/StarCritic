package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Objects;

@Entity
@Table(name = "critica")
@Inheritance(strategy = InheritanceType.JOINED)
public class Critica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCritica;
    private int puntuacion;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "ID_usuario_registrado")
    private UsuarioRegistrado usuarioRegistrado;
    @Transient
    private String nombreUsuario;
    @Transient
    @Enumerated(EnumType.STRING)
    private Roles rol;

    public Long getIdCritica() {
        return idCritica;
    }

    public void setIdCritica(Long idCritica) {
        this.idCritica = idCritica;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UsuarioRegistrado getUsuarioRegistrado() {
        return usuarioRegistrado;
    }

    public void setUsuarioRegistrado(UsuarioRegistrado usuarioRegistrado) {
        this.usuarioRegistrado = usuarioRegistrado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Critica)) return false;
        Critica critica = (Critica) o;
        return Objects.equals(idCritica, critica.idCritica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCritica);
    }

    @Override
    public String toString() {
        return "Critica{" +
                "idCritica=" + idCritica +
                ", puntuacion=" + puntuacion +
                ", descripcion='" + descripcion + '\'' +
                ", idUsuarioRegistrado=" + (usuarioRegistrado != null ? usuarioRegistrado.getIdUsuario() : null) +
                '}';
    }
}
