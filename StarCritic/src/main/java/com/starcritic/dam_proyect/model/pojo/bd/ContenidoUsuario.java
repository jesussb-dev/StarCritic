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
public class ContenidoUsuario {
 
    private int idUsuarioRegistrado;
    private int idContenido;
    private LocalDate fechaVisita;
    private int numVisitas;

    public ContenidoUsuario(int idUsuarioRegistrado, int idContenido,
                            LocalDate fechaVisita, int numVisitas) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.idContenido = idContenido;
        this.fechaVisita = fechaVisita;
        this.numVisitas = numVisitas;
    }
    public ContenidoUsuario(int idUsuarioRegistrado,
                            LocalDate fechaVisita, int numVisitas) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.idContenido = 0;
        this.fechaVisita = fechaVisita;
        this.numVisitas = numVisitas;
    }
    public int getIdUsuarioRegistrado() {
        return idUsuarioRegistrado;
    }

    public void setIdUsuarioRegistrado(int idUsuarioRegistrado) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public LocalDate getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(LocalDate fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public int getNumVisitas() {
        return numVisitas;
    }

    public void setNumVisitas(int numVisitas) {
        this.numVisitas = numVisitas;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContenidoUsuario)) return false;
        ContenidoUsuario that = (ContenidoUsuario) o;
        return idUsuarioRegistrado == that.idUsuarioRegistrado &&
                idContenido == that.idContenido;
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(idUsuarioRegistrado, idContenido);
    }
 
    @Override
    public String toString() {
        return "ContenidoUsuario{" +
                "idUsuarioRegistrado=" + idUsuarioRegistrado +
                ", idContenido=" + idContenido +
                ", fechaVisita=" + fechaVisita +
                ", numVisitas=" + numVisitas +
                '}';
    }
}
