package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "critica_videojuego")
@PrimaryKeyJoinColumn(name = "ID_critica_videojuego")
public class CriticaVideojuego extends Critica {
    @ManyToOne
    @JoinColumn(name = "ID_videojuego")
    private Videojuego videojuego;
    @ManyToOne
    @JoinColumn(name = "ID_aspecto")
    private Aspecto aspecto;

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }

    public Aspecto getAspecto() {
        return aspecto;
    }

    public void setAspecto(Aspecto aspecto) {
        this.aspecto = aspecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CriticaVideojuego)) return false;
        if (!super.equals(o)) return false;
        CriticaVideojuego that = (CriticaVideojuego) o;
        return Objects.equals(videojuego, that.videojuego);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), videojuego);
    }

    @Override
    public String toString() {
        return "CriticaVideojuego{" +
                "idCritica=" + getIdCritica() +
                ", puntuacion=" + getPuntuacion() +
                ", aspecto=" + aspecto +
                ", videojuego=" + videojuego +
                '}';
    }
}
