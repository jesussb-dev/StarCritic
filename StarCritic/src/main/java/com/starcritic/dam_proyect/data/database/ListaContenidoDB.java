package com.starcritic.dam_proyect.data.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.ListaContenido;
import java.util.HashMap;

/**
 * Contenido dentro de las listas de un usuario, vía API REST.
 *
 * @author Jesús Santos Baquero
 */
public class ListaContenidoDB {

    /**
     * Añadir un contenido a una lista de un usuario.
     * @param listaContenido la relación lista-contenido a crear.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean crearListaContenido(ListaContenido listaContenido) {
        JsonObject usuario = new JsonObject();
        usuario.addProperty("idUsuario", listaContenido.getIdUsuarioRegistrado());
        JsonObject contenido = new JsonObject();
        contenido.addProperty("idContenido", listaContenido.getIdContenido());
        JsonObject id = new JsonObject();
        id.addProperty("nombreLista", listaContenido.getNombreLista());

        JsonObject body = new JsonObject();
        body.add("usuario", usuario);
        body.add("contenido", contenido);
        body.add("id", id);

        return ApiClient.get().postOk("/listas-contenido", body);
    }

    /**
     * Añadir un contenido a una lista comprobando antes que no este duplicado.
     * @param idUsuarioRegistrado el identificador del usuario propietario.
     * @param nombreLista el nombre de la lista.
     * @param idContenido el identificador del contenido a añadir.
     * @return true si la operación fue exitosa, false si ya existia o falló.
     */
    public static boolean anadirContenidoALista(int idUsuarioRegistrado, String nombreLista, int idContenido) {
        if (existeContenidoEnLista(idUsuarioRegistrado, nombreLista, idContenido)) {
            return false;
        }
        return crearListaContenido(new ListaContenido(idUsuarioRegistrado, nombreLista, idContenido));
    }

    /**
     * Comprobar si un contenido se encuentra en una lista concreta de un usuario.
     * @param idUsuarioRegistrado el identificador del usuario propietario.
     * @param nombreLista el nombre de la lista.
     * @param idContenido el identificador del contenido a buscar.
     * @return true si el contenido está en la lista, false en caso contrario.
     */
    public static boolean existeContenidoEnLista(int idUsuarioRegistrado, String nombreLista, int idContenido) {
        return ApiClient.get().getBoolean("/listas-contenido/usuario/" + idUsuarioRegistrado
                + "/lista/" + ApiClient.enc(nombreLista) + "/contenido/" + idContenido + "/existe");
    }

    /**
     * Obtener todos los contenidos que se encuentran en una lista de un usuario.
     * @param idUsuarioRegistrado el identificador del usuario propietario.
     * @param nombreLista el nombre de la lista.
     * @return un mapa con el id del contenido y su titulo.
     */
    public static HashMap<Integer, String> obtenerContenidoPorLista(int idUsuarioRegistrado, String nombreLista) {
        HashMap<Integer, String> contenidos = new HashMap<>();
        JsonElement resp = ApiClient.get().getJson("/listas-contenido/usuario/" + idUsuarioRegistrado
                + "/lista/" + ApiClient.enc(nombreLista));
        if (resp == null || !resp.isJsonArray()) {
            return contenidos;
        }
        JsonArray arr = resp.getAsJsonArray();
        for (JsonElement el : arr) {
            JsonObject obj = el.getAsJsonObject();
            JsonObject contenido = obj.getAsJsonObject("contenido");
            if (contenido != null && contenido.has("idContenido")) {
                int id = contenido.get("idContenido").getAsInt();
                String titulo = contenido.has("titulo") && !contenido.get("titulo").isJsonNull()
                        ? contenido.get("titulo").getAsString() : "";
                contenidos.put(id, titulo);
            }
        }
        return contenidos;
    }

    /**
     * Quitar un contenido de una lista de un usuario.
     * @param idUsuarioRegistrado el identificador del usuario propietario.
     * @param nombreLista el nombre de la lista.
     * @param idContenido el identificador del contenido a quitar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean eliminarContenidoDeLista(int idUsuarioRegistrado, String nombreLista, int idContenido) {
        return ApiClient.get().delete("/listas-contenido/usuario/" + idUsuarioRegistrado
                + "/lista/" + ApiClient.enc(nombreLista) + "/contenido/" + idContenido);
    }
}
