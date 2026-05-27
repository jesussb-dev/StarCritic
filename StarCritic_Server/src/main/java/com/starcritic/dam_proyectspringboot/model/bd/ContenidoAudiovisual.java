package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * @author jsb
 */
@Entity
@Table(name = "contenido_audiovisual")
@PrimaryKeyJoinColumn(name = "ID_contenido_audiovisual")
public class ContenidoAudiovisual extends Contenido {

    @Column(name = "ID_Api")
    private String idOmdb;

    public ContenidoAudiovisual() {
        setOrigen(Origen.OMDB);
    }

 

    public String getIdOmdb() {
        return idOmdb;
    }

    public void setIdOmdb(String idOmdb) {
        this.idOmdb = idOmdb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOmdb);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final ContenidoAudiovisual other = (ContenidoAudiovisual) obj;
        return Objects.equals(this.idOmdb, other.idOmdb);
    }

    @Override
    public String toString() {
        return "ContenidoAudiovisual{"
                + "idContenido=" + getIdContenido()
                + ", titulo=" + getTitulo()
                + ", idOmdb='" + idOmdb + '\''
                + '}';
    }
}