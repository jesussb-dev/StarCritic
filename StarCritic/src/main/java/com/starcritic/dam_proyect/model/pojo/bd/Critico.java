/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author jsb
 */
public class Critico extends UsuarioRegistrado{
    private String certificacion;
    private EstadoCertificacion estado;
    public Critico(int idUsuario, String nombreUsuario, String correoElectronico, LocalDate fechaCreacion, String nombre, 
            String apellido1, String apellido2, Roles rol, String contrasenha, Boolean baneado, String certificacion, EstadoCertificacion estado) {
        super(idUsuario, nombreUsuario, correoElectronico, fechaCreacion, nombre, apellido1, apellido2, rol, contrasenha, baneado);
        this.certificacion = certificacion;
        this.estado = estado;
    }
    public Critico(String nombreUsuario, String correoElectronico, LocalDate fechaCreacion, String nombre, 
            String apellido1, String apellido2, Roles rol, String contrasenha, Boolean baneado, String certificacion, EstadoCertificacion estado) {
        super( nombreUsuario, correoElectronico, fechaCreacion, nombre, apellido1, apellido2, rol, contrasenha, baneado);
        this.certificacion = certificacion;
        this.estado = estado;
    }

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
