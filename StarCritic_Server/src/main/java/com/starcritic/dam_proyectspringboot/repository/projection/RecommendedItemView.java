package com.starcritic.dam_proyectspringboot.repository.projection;

/**
 * Proyeccion de una recomendacion. Las consultas nativas deben aliasar las
 * columnas con los nombres de propiedad (idContenido, posterKey, apiId, etc.).
 * @author Jesús Santos Baquero
 */
public class RecommendedItemView {

    private Long idContenido;
    private String titulo;
    private String posterKey;
    private String apiId;
    private String origen;
    private String tipoContenido;
    private Double score;

    public RecommendedItemView() {
    }

    public RecommendedItemView(Number idContenido, String titulo, String posterKey,
                               String apiId, String origen, String tipoContenido, Number score) {
        this.idContenido = (idContenido == null) ? null : idContenido.longValue();
        this.titulo = titulo;
        this.posterKey = posterKey;
        this.apiId = apiId;
        this.origen = origen;
        this.tipoContenido = tipoContenido;
        this.score = (score == null) ? null : score.doubleValue();
    }

    public Long getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Long idContenido) {
        this.idContenido = idContenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPosterKey() {
        return posterKey;
    }

    public void setPosterKey(String posterKey) {
        this.posterKey = posterKey;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Number score) {
        this.score = (score == null) ? null : score.doubleValue();
    }
}
