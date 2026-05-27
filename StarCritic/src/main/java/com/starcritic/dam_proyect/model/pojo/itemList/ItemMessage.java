package com.starcritic.dam_proyect.model.pojo.itemList;

import java.time.LocalDateTime;

/**
 * Item de la bandeja de entrada listo para pintar con
 * {@link ItemMessageRender}. Encapsula los datos del mensaje y del remitente.
 *
 * @author Jesús Santos Baquero
 */
public class ItemMessage {

    private int idMensaje;
    private int idRemitente;
    private String nombreRemitente;
    private String asunto;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean leido;

    public ItemMessage(int idMensaje, int idRemitente, String nombreRemitente,
                       String asunto, String contenido, LocalDateTime fechaEnvio, boolean leido) {
        this.idMensaje = idMensaje;
        this.idRemitente = idRemitente;
        this.nombreRemitente = nombreRemitente;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
    }

    public int getIdMensaje() { return idMensaje; }
    public void setIdMensaje(int idMensaje) { this.idMensaje = idMensaje; }

    public int getIdRemitente() { return idRemitente; }
    public void setIdRemitente(int idRemitente) { this.idRemitente = idRemitente; }

    public String getNombreRemitente() { return nombreRemitente; }
    public void setNombreRemitente(String nombreRemitente) { this.nombreRemitente = nombreRemitente; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public boolean isLeido() { return leido; }
    public void setLeido(boolean leido) { this.leido = leido; }
}
