/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "critico")
@PrimaryKeyJoinColumn(name = "ID_critico")
public class Critico extends UsuarioRegistrado{
    private String certificacion;
    @Column(name = "estado_certificacion")
    private EstadoCertificacion estado;


    public String getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public EstadoCertificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoCertificacion estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.certificacion);
        hash = 59 * hash + Objects.hashCode(this.estado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Critico other = (Critico) obj;
        if (!Objects.equals(this.certificacion, other.certificacion)) {
            return false;
        }
        return this.estado == other.estado;
    }
    
}
