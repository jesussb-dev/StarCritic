/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyectspringboot.model.bd;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "usuario_registrado")
@Inheritance(strategy = InheritanceType.JOINED)
public class UsuarioRegistrado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_usuario_registrado")
    private Long idUsuario;
    private String nombreUsuario;
    private String correoElectronico;
    private LocalDate fechaCreacion;
    private String nombre;
    private String apellido1;
    private String apellido2;
    @Enumerated(EnumType.STRING)
    private Roles rol;
    private String contrasenha;
    private boolean baneado;


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public Boolean isBaneado() {
        return baneado;
    }

    public void setBaneado(Boolean baneado) {
        this.baneado = baneado;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }



    @Override
    public String toString() {
        return "UsuarioRegistrado{" +
                "idUsuario=" + getIdUsuario() +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", rol='" + rol + '\'' +
                ", baneado=" + baneado +
                '}';
    }
}
