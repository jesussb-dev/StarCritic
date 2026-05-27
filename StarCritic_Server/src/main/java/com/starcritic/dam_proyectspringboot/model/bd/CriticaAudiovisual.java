package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "critica_audiovisual")
@PrimaryKeyJoinColumn(name = "ID_critica_audiovisual")
public class CriticaAudiovisual extends Critica {
    @ManyToOne
    @JoinColumn(name = "ID_contenido_audiovisual")
    private ContenidoAudiovisual contenidoAudiovisual;
    @ManyToOne
    @JoinColumn(name = "ID_aspecto")
    private Aspecto aspecto;

    public ContenidoAudiovisual getContenidoAudiovisual() {
        return contenidoAudiovisual;
    }

    public void setContenidoAudiovisual(ContenidoAudiovisual contenidoAudiovisual) {
        this.contenidoAudiovisual = contenidoAudiovisual;
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
        if (!(o instanceof CriticaAudiovisual)) return false;
        if (!super.equals(o)) return false;
        CriticaAudiovisual that = (CriticaAudiovisual) o;
        return Objects.equals(contenidoAudiovisual, that.contenidoAudiovisual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contenidoAudiovisual);
    }

    @Override
    public String toString() {
        return "CriticaAudiovisual{" +
                "idCritica=" + getIdCritica() +
                ", puntuacion=" + getPuntuacion() +
                ", aspecto=" + aspecto +
                ", contenidoAudiovisual=" + contenidoAudiovisual +
                '}';
    }
}
