package com.starcritic.dam_proyect.model.pojo.bd;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Clase base de un contenido del catálogo (audiovisual o videojuego). Las
 * subclases {@link ContenidoAudiovisual} y {@link Videojuego} añaden los
 * identificadores externos específicos.
 *
 * @author Jesús Santos Baquero
 */
public class Contenido {

    private int idContenido;
    private LocalDate fecha;
    private Origen origen = Origen.LOCAL;
    private boolean destacado = false;
    private boolean oculto = false;
    private String titulo;
    private String sinopsis;
    private String posterKey;
    private TipoContenido tipoContenido;

    public Contenido() {
    }

    public Contenido(int idContenido, LocalDate fecha, Origen origen,
            boolean destacado, boolean oculto, String titulo,
            String sinopsis, String posterKey,
            TipoContenido tipoContenido) {

        this.idContenido = idContenido;
        this.fecha = fecha;
        this.origen = origen;
        this.destacado = destacado;
        this.oculto = oculto;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.posterKey = posterKey;
        this.tipoContenido = tipoContenido;
    }

    public Contenido(LocalDate fecha, Origen origen,
            boolean destacado, boolean oculto, String titulo,
            String sinopsis, String posterKey,
            TipoContenido tipoContenido) {

        this.fecha = fecha;
        this.origen = origen;
        this.destacado = destacado;
        this.oculto = oculto;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.posterKey = posterKey;
        this.tipoContenido = tipoContenido;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPosterKey() {
        return posterKey;
    }

    public void setPosterKey(String posterKey) {
        this.posterKey = posterKey;
    }

    public TipoContenido getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(TipoContenido tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContenido);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Contenido other = (Contenido) obj;
        return this.idContenido == other.idContenido;
    }

    @Override
    public String toString() {
        return "Contenido{"
                + "idContenido=" + idContenido
                + ", fecha=" + fecha
                + ", origen=" + origen
                + ", destacado=" + destacado
                + ", oculto=" + oculto
                + ", titulo='" + titulo + '\''
                + ", sinopsis='" + sinopsis + '\''
                + ", posterKey='" + posterKey + '\''
                + ", tipoContenido=" + tipoContenido
                + '}';
    }
}