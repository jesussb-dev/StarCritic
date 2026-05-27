package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Jesús Santos Baquero
 */
@Entity
@Table(name = "lista_usuario")
@IdClass(ListaUsuario.PK.class)
public class ListaUsuario {

    @Id
    @Column(name = "ID_usuario_registrado")
    private Long idUsuarioRegistrado;

    @Id
    @Column(name = "nombre_lista")
    private String nombreLista;

    private LocalDate fechaCreacion;

    public ListaUsuario() {}

    public ListaUsuario(Long idUsuarioRegistrado, String nombreLista, LocalDate fechaCreacion) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
        this.nombreLista = nombreLista;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdUsuarioRegistrado() {
        return idUsuarioRegistrado;
    }

    public void setIdUsuarioRegistrado(Long idUsuarioRegistrado) {
        this.idUsuarioRegistrado = idUsuarioRegistrado;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListaUsuario)) return false;
        ListaUsuario that = (ListaUsuario) o;
        return Objects.equals(idUsuarioRegistrado, that.idUsuarioRegistrado)
                && Objects.equals(nombreLista, that.nombreLista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuarioRegistrado, nombreLista);
    }

    @Override
    public String toString() {
        return "ListaUsuario{" +
                "idUsuarioRegistrado=" + idUsuarioRegistrado +
                ", nombreLista='" + nombreLista + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }

    public static class PK implements Serializable {
        private Long idUsuarioRegistrado;
        private String nombreLista;

        public PK() {}

        public PK(Long idUsuarioRegistrado, String nombreLista) {
            this.idUsuarioRegistrado = idUsuarioRegistrado;
            this.nombreLista = nombreLista;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PK)) return false;
            PK pk = (PK) o;
            return Objects.equals(idUsuarioRegistrado, pk.idUsuarioRegistrado)
                    && Objects.equals(nombreLista, pk.nombreLista);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idUsuarioRegistrado, nombreLista);
        }
    }
}
