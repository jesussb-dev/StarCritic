package com.starcritic.dam_proyect.model.pojo.bd;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author jsb
 */
public class ContenidoAudiovisual extends Contenido {

    private String idOmdb;

    public ContenidoAudiovisual() {
        setOrigen(Origen.OMDB);
    }

    public ContenidoAudiovisual(int idContenido, LocalDate fecha,boolean destacado, boolean oculto,String titulo, String sinopsis,String posterKey, TipoContenido tipoContenido,String idOmdb) {

        super(idContenido, fecha, Origen.OMDB,
                destacado, oculto,
                titulo, sinopsis,
                posterKey, tipoContenido);

        this.idOmdb = idOmdb;
    }

    public ContenidoAudiovisual(LocalDate fecha,
            boolean destacado, boolean oculto,
            String titulo, String sinopsis,
            String posterKey, TipoContenido tipoContenido,
            String idOmdb) {

        super(fecha, Origen.OMDB,
                destacado, oculto,
                titulo, sinopsis,
                posterKey, tipoContenido);

        this.idOmdb = idOmdb;
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