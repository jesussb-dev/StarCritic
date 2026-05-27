/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Lista personal creada por un usuario. La identifica el par
 * (idUsuarioRegistrado, nombreLista).
 *
 * @author Jesús Santos Baquero
 */
public class ListaUsuario {
 
    private int idUsuarioRegistrado;
    private String nombreLista;
    private LocalDate fechaCreacion;

    public ListaUsuario(int idUsuarioRegistrado, String nombreLista, LocalDate fechaCreacion) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.nombreLista = nombreLista;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdUsuarioRegistrado() {
        return idUsuarioRegistrado;
    }

    public void setIdUsuarioRegistrado(int idUsuarioRegistrado) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
    }
 
    public String getNombreLista() {
        return nombreLista;
    }
 
    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }
 
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
 
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListaUsuario)) return false;
        ListaUsuario that = (ListaUsuario) o;
        return idUsuarioRegistrado == that.idUsuarioRegistrado &&
                Objects.equals(nombreLista, that.nombreLista);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(idUsuarioRegistrado, nombreLista);
    }
 
    @Override
    public String toString() {
        return "ListaUsuario{" +
                "idUsuarioRegistrado=" + idUsuarioRegistrado +
                ", nombreLista='" + nombreLista + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
