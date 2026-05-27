/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "aspecto")
public class Aspecto {

    public enum Categoria {AUDIOVISUAL, VIDEOJUEGO, AMBOS}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAspecto;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria tipoContenido;


    public Long getIdAspecto() {
        return idAspecto;
    }

    public void setIdAspecto(Long idAspecto) {
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
    public int hashCode() {
        return Objects.hash(idAspecto, nombre, tipoContenido);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Aspecto other = (Aspecto) obj;
        return Objects.equals(this.idAspecto, other.idAspecto)
                && Objects.equals(this.nombre, other.nombre)
                && this.tipoContenido == other.tipoContenido;
    }


}
