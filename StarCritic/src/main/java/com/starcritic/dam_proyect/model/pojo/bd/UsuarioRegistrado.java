/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Usuario registrado en StarCritic. Mantiene credenciales, datos personales,
 * avatar y rol; los administradores son superusuarios y los críticos
 * acreditados tienen su crítica destacada en la ficha.
 *
 * @author Jesús Santos Baquero
 */
public class UsuarioRegistrado {

    private int idUsuario;
    private String nombreUsuario;
    private String correoElectronico;
    private LocalDate fechaCreacion;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Roles rol;
    private String contrasenha;
    private boolean baneado;


    public UsuarioRegistrado(int idUsuario, String nombreUsuario,
                             String correoElectronico, LocalDate fechaCreacion, String nombre,
                             String apellido1, String apellido2, Roles rol,
                             String contrasenha, Boolean baneado) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correoElectronico = correoElectronico;
        this.fechaCreacion = fechaCreacion;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.rol = rol;
        this.contrasenha = contrasenha;
        this.baneado = baneado;
    }
    public UsuarioRegistrado(String nombreUsuario,
                             String correoElectronico, LocalDate fechaCreacion, String nombre,
                             String apellido1, String apellido2, Roles rol,
                             String contrasenha, Boolean baneado) {
        this.nombreUsuario = nombreUsuario;
        this.correoElectronico = correoElectronico;
        this.fechaCreacion = fechaCreacion;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.rol = rol;

        this.contrasenha = contrasenha;
        this.baneado = baneado;
    }
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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
