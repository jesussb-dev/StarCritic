/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

import java.util.Objects;

/**
 *
 * @author jsb
 */
public class ListaContenido {
 
    private int idUsuarioRegistrado;
    private String nombreLista;
    private int idContenido;


    public ListaContenido(int idUsuarioRegistrado, String nombreLista, int idContenido) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.nombreLista = nombreLista;
        this.idContenido = idContenido;
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

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListaContenido)) return false;
        ListaContenido that = (ListaContenido) o;
        return idUsuarioRegistrado == that.idUsuarioRegistrado &&
                idContenido == that.idContenido &&
                Objects.equals(nombreLista, that.nombreLista);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(idUsuarioRegistrado, nombreLista, idContenido);
    }
 
    @Override
    public String toString() {
        return "ListaContenido{" +
                "idUsuarioRegistrado=" + idUsuarioRegistrado +
                ", nombreLista='" + nombreLista + '\'' +
                ", idContenido=" + idContenido +
                '}';
    }
}
