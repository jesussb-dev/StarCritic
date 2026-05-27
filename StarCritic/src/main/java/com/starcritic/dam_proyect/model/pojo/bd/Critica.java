/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

import java.util.Objects;

/**
 * Crítica base hecha por un usuario sobre un contenido. Las subclases
 * {@link CriticaAudiovisual} y {@link CriticaVideojuego} añaden los campos
 * específicos de cada tipo de contenido.
 *
 * @author Jesús Santos Baquero
 */
public class Critica {
 
    private int idCritica;
    private int puntuacion;
    private String descripcion;
    private int idUsuarioRegistrado;
    private String nombreUsuario;
    private Roles rol;

    public Critica(int idCritica, int puntuacion, String descripcion, int idUsuarioRegistrado) {
        this.idCritica = idCritica;
        this.puntuacion = puntuacion;
        this.descripcion = descripcion;
        this.idUsuarioRegistrado = idUsuarioRegistrado;
    }

    public Critica(int puntuacion, String descripcion, int idUsuarioRegistrado) {
        this.puntuacion = puntuacion;
        this.descripcion = descripcion;
        this.idUsuarioRegistrado = idUsuarioRegistrado;
    }
    public Critica(int puntuacion, String descripcion, int idUsuarioRegistrado, String nombreUsuario, Roles rol) {
        this.puntuacion = puntuacion;
        this.descripcion = descripcion;
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }
    public int getIdCritica() {
        return idCritica;
    }
 
    public void setIdCritica(int idCritica) {
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
 
    public int getIdUsuarioRegistrado() {
        return idUsuarioRegistrado;
    }
 
    public void setIdUsuarioRegistrado(int idUsuarioRegistrado) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
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
                ", idUsuarioRegistrado=" + idUsuarioRegistrado +
                '}';
    }
}
