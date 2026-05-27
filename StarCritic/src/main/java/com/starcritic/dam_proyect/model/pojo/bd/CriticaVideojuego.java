/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

import java.util.Objects;

/**
 * Crítica de un videojuego. Asocia la crítica con el videojuego concreto
 * al que aplica.
 *
 * @author Jesús Santos Baquero
 */
public class CriticaVideojuego extends CriticaConAspecto {

    private int idVideojuego;

    public CriticaVideojuego(int idCritica, int puntuacion, String descripcion,
                             int idUsuarioRegistrado, int idAspecto, int idVideojuego) {
        super(idCritica, puntuacion, descripcion, idUsuarioRegistrado, idAspecto);
        this.idVideojuego = idVideojuego;
    }

    public CriticaVideojuego(int puntuacion, String descripcion,
                             int idUsuarioRegistrado, int idAspecto, int idVideojuego) {
        super(puntuacion, descripcion, idUsuarioRegistrado, idAspecto);
        this.idVideojuego = idVideojuego;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }
 
    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CriticaVideojuego)) return false;
        if (!super.equals(o)) return false;
        CriticaVideojuego that = (CriticaVideojuego) o;
        return idVideojuego == that.idVideojuego;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idVideojuego);
    }

    @Override
    public String toString() {
        return "CriticaVideojuego{" +
                "idCritica=" + getIdCritica() +
                ", puntuacion=" + getPuntuacion() +
                ", idAspecto=" + getIdAspecto() +
                ", idVideojuego=" + idVideojuego +
                '}';
    }
}
