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
public class Aspecto {

    public enum Categoria {AUDIOVISUAL, VIDEOJUEGO, AMBOS}

    private int idAspecto;
    private String nombre;
    private Categoria tipoContenido;

    public Aspecto(int idAspecto, String nombre, Categoria tipoContenido) {
        this.idAspecto = idAspecto;
        this.nombre = nombre;
        this.tipoContenido = tipoContenido;
    }

    public int getIdAspecto() {
        return idAspecto;
    }

    public void setIdAspecto(int idAspecto) {
        this.idAspecto = idAspecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(Categoria tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aspecto)) return false;
        Aspecto aspecto = (Aspecto) o;
        return idAspecto == aspecto.idAspecto;
    }

    @Override
    public int hashCode() {
        return idAspecto;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
