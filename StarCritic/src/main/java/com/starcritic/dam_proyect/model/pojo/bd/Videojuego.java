package com.starcritic.dam_proyect.model.pojo.bd;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author jsb
 */
public class Videojuego extends Contenido {

    private int idRawg;

    public Videojuego() {
        setOrigen(Origen.RAWG);
        setTipoContenido(TipoContenido.VIDEOJUEGO);
    }

    public Videojuego(int idContenido, LocalDate fecha,boolean destacado, boolean oculto,String titulo, String sinopsis, String posterKey, int idRawg) {
        super(idContenido, fecha, Origen.RAWG,
                destacado, oculto,
                titulo, sinopsis,
                posterKey, TipoContenido.VIDEOJUEGO);

        this.idRawg = idRawg;
    }

    public Videojuego(LocalDate fecha,
            boolean destacado, boolean oculto,
            String titulo, String sinopsis,
            String posterKey, int idRawg) {

        super(fecha, Origen.RAWG,
                destacado, oculto,
                titulo, sinopsis,
                posterKey, TipoContenido.VIDEOJUEGO);

        this.idRawg = idRawg;
    }

    public int getIdRawg() {
        return idRawg;
    }

    public void setIdRawg(int idRawg) {
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
        return this.idRawg == other.idRawg;
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
