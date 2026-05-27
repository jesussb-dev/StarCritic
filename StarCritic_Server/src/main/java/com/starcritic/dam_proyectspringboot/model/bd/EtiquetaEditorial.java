package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
/**
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "etiqueta_editorial")
public class EtiquetaEditorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtiqueta;
    private String nombre;

    public EtiquetaEditorial() {}

    public EtiquetaEditorial(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(Long idEtiqueta) {
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
        return Objects.equals(idEtiqueta, that.idEtiqueta);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idEtiqueta);
        hash = 59 * hash + Objects.hashCode(this.nombre);
        return hash;
    }


}
