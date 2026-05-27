package com.starcritic.dam_proyect.model.pojo.bd;

import java.util.Objects;

public class EtiquetaEditorial {

    private int idEtiqueta;
    private String nombre;

    public EtiquetaEditorial(int idEtiqueta, String nombre) {
        this.idEtiqueta = idEtiqueta;
        this.nombre = nombre;
    }

    public EtiquetaEditorial(String nombre) {
        this.nombre = nombre;
    }

    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(int idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtiquetaEditorial)) {
            return false;
        }
        EtiquetaEditorial that = (EtiquetaEditorial) o;
        return idEtiqueta == that.idEtiqueta;
    }

    @Override
    public int hashCode() {
        return idEtiqueta;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
