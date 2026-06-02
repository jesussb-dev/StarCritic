package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "contenido")
@Inheritance(strategy = InheritanceType.JOINED)
public class Contenido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContenido;
    private LocalDate fecha;
    @Enumerated(EnumType.STRING)
    private Origen origen = Origen.LOCAL;
    private boolean destacado = false;
    private boolean oculto = false;
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String sinopsis;
    private String posterKey;
    @Enumerated(EnumType.STRING)
    private TipoContenido tipoContenido;

    public Long getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Long idContenido) {
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
        return Objects.equals(this.idContenido, other.idContenido);
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