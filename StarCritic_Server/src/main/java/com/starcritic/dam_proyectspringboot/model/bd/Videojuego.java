package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "videojuego")
@PrimaryKeyJoinColumn(name = "ID_videojuego")
public class Videojuego extends Contenido {

    @Column(name = "ID_Api")
    private Integer idRawg;

    public Videojuego() {
        setOrigen(Origen.RAWG);
        setTipoContenido(TipoContenido.VIDEOJUEGO);
    }



    public Integer getIdRawg() {
        return idRawg;
    }

    public void setIdRawg(Integer idRawg) {
        this.idRawg = idRawg;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRawg);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Videojuego other = (Videojuego) obj;
        return Objects.equals(this.idRawg, other.idRawg);
    }

    @Override
    public String toString() {
        return "Videojuego{"
                + "idContenido=" + getIdContenido()
                + ", titulo=" + getTitulo()
                + ", idRawg=" + idRawg
                + '}';
    }
}
