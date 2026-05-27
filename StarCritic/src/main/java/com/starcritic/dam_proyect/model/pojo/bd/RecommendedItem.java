package com.starcritic.dam_proyect.model.pojo.bd;

/**
 * DTO ligero para el algoritmo de recomendaciones.
 * apiId es null para contenido LOCAL; en ese caso se usa idContenido para navegar al detalle.
 *
 * @author Jesús Santos Baquero
 */
public class RecommendedItem {

    private final int idContenido;
    private final String titulo;
    private final String posterKey;
    private final String apiId;
    private final Origen origen;
    private final TipoContenido tipo;

    public RecommendedItem(int idContenido, String titulo, String posterKey,
                           String apiId, Origen origen, TipoContenido tipo) {
        this.idContenido = idContenido;
        this.titulo      = titulo;
        this.posterKey   = posterKey;
        this.apiId       = (apiId != null && !apiId.isBlank()) ? apiId : null;
        this.origen      = origen;
        this.tipo        = tipo;
    }

    public int getIdContenido()  { return idContenido; }
    public String getTitulo()    { return titulo; }
    public String getPosterKey() { return posterKey; }
    public String getApiId()     { return apiId; }
    public Origen getOrigen()    { return origen; }
    public TipoContenido getTipo() { return tipo; }

    /** ID que debe pasarse a CompleteItemController: apiId si existe, si no el id de BD. */
    public String getEffectiveId() {
        return apiId != null ? apiId : String.valueOf(idContenido);
    }
}
