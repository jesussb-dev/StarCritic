package com.starcritic.dam_proyect.model.pojo.bd;

/**
 * Clase abstracta que representa una crítica que evalúa un aspecto concreto
 * del contenido (audiovisual o videojuego). Hereda de {@link Critica}.
 *
 * @author Jesús Santos Baquero
 */
public abstract class CriticaConAspecto extends Critica {

    private int idAspecto;

    protected CriticaConAspecto(int idCritica, int puntuacion, String descripcion,
                                 int idUsuarioRegistrado, int idAspecto) {
        super(idCritica, puntuacion, descripcion, idUsuarioRegistrado);
        this.idAspecto = idAspecto;
    }

    protected CriticaConAspecto(int puntuacion, String descripcion,
                                 int idUsuarioRegistrado, int idAspecto) {
        super(puntuacion, descripcion, idUsuarioRegistrado);
        this.idAspecto = idAspecto;
    }

    public int getIdAspecto() {
        return idAspecto;
    }

    public void setIdAspecto(int idAspecto) {
        this.idAspecto = idAspecto;
    }
}
