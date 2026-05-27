package com.starcritic.dam_proyectspringboot.repository.projection;

/**
 * Proyeccion clave-valor para las consultas agregadas de estadisticas.
 * Las consultas nativas deben aliasar las columnas como {@code k} y {@code v}.
 * @author Jesús Santos Baquero
 */
public class KeyValueView {

    private String k;
    private Double v;

    public KeyValueView() {
    }

    public KeyValueView(String k, Number v) {
        this.k = k;
        this.v = (v == null) ? null : v.doubleValue();
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public Double getV() {
        return v;
    }

    public void setV(Number v) {
        this.v = (v == null) ? null : v.doubleValue();
    }
}
