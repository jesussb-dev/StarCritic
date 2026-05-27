package com.starcritic.dam_proyectspringboot.repository.projection;

import com.starcritic.dam_proyectspringboot.model.bd.EstadoCertificacion;

/**
 * Cuerpo de la peticion para promover un usuario a critico.
 * @author Jesús Santos Baquero
 */
public class PromocionRequest {

    private String certificacion;
    private EstadoCertificacion estado;

    public PromocionRequest() {
    }

    public PromocionRequest(String certificacion, EstadoCertificacion estado) {
        this.certificacion = certificacion;
        this.estado = estado;
    }

    public String certificacion() {
        return certificacion;
    }

    public EstadoCertificacion estado() {
        return estado;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public void setEstado(EstadoCertificacion estado) {
        this.estado = estado;
    }
}
