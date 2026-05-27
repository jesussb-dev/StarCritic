/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.itemList;

import com.starcritic.dam_proyect.model.pojo.bd.Roles;

/**
 * Item de la lista de críticas listo para pintar con {@link ItemCriticRender}.
 * Aplana autor, rol, texto y puntuación junto con la marca de crítico
 * verificado.
 *
 * @author Jesús Santos Baquero
 */
public class ItemCritic {
    private int idCritica;
    private String nombreUsuario;
    private boolean esCritico;
    private Roles tipoUsuario;
    private String critica;
    private double puntuacion;

    public ItemCritic(int idCritica,String nombreUsuario, Roles tipoUsuario, String critica, double puntuacion, boolean esCritico) {
        this.idCritica = idCritica;
        this.nombreUsuario = nombreUsuario;
        this.esCritico = esCritico;
        this.tipoUsuario = tipoUsuario;
        this.critica = critica;
        this.puntuacion = puntuacion;
    }

    public int getIdCritica() {
        return idCritica;
    }

    public void setIdCritica(int idCritica) {
        this.idCritica = idCritica;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Roles getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Roles tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCritica() {
        return critica;
    }

    public void setCritica(String critica) {
        this.critica = critica;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public boolean isEsCritico() {
        return esCritico;
    }

    public void setEsCritico(boolean esCritico) {
        this.esCritico = esCritico;
    }

}
