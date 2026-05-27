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
public class CriticaAudiovisual extends CriticaConAspecto {

    private int idContenidoAudiovisual;

    public CriticaAudiovisual(int idCritica, int puntuacion, String descripcion,
                              int idUsuarioRegistrado, int idAspecto,
                              int idContenidoAudiovisual) {
        super(idCritica, puntuacion, descripcion, idUsuarioRegistrado, idAspecto);
        this.idContenidoAudiovisual = idContenidoAudiovisual;
    }

    public CriticaAudiovisual(int puntuacion, String descripcion,
                              int idUsuarioRegistrado, int idAspecto,
                              int idContenidoAudiovisual) {
        super(puntuacion, descripcion, idUsuarioRegistrado, idAspecto);
        this.idContenidoAudiovisual = idContenidoAudiovisual;
    }

    public int getIdContenidoAudiovisual() {
        return idContenidoAudiovisual;
    }
 
    public void setIdContenidoAudiovisual(int idContenidoAudiovisual) {
        this.idContenidoAudiovisual = idContenidoAudiovisual;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CriticaAudiovisual)) return false;
        if (!super.equals(o)) return false;
        CriticaAudiovisual that = (CriticaAudiovisual) o;
        return Objects.equals(idContenidoAudiovisual, that.idContenidoAudiovisual);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idContenidoAudiovisual);
    }
 
    @Override
    public String toString() {
        return "CriticaAudiovisual{" +
                "idCritica=" + getIdCritica() +
                ", puntuacion=" + getPuntuacion() +
                ", idAspecto=" + getIdAspecto() +
                ", idContenidoAudiovisual=" + idContenidoAudiovisual +
                '}';
    }
}