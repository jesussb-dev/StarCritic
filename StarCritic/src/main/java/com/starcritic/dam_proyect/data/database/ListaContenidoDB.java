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

    public static boolean anadirContenidoALista(int idUsuarioRegistrado, String nombreLista, int idContenido) {
        if (existeContenidoEnLista(idUsuarioRegistrado, nombreLista, idContenido)) {
            return false;
        }
        return crearListaContenido(new ListaContenido(idUsuarioRegistrado, nombreLista, idContenido));
    }

    public static boolean existeContenidoEnLista(int idUsuarioRegistrado, String nombreLista, int idContenido) {
        return ApiClient.get().getBoolean("/listas-contenido/usuario/" + idUsuarioRegistrado
                + "/lista/" + ApiClient.enc(nombreLista) + "/contenido/" + idContenido + "/existe");
    }

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

    public static boolean eliminarContenidoDeLista(int idUsuarioRegistrado, String nombreLista, int idContenido) {
        return ApiClient.get().delete("/listas-contenido/usuario/" + idUsuarioRegistrado
                + "/lista/" + ApiClient.enc(nombreLista) + "/contenido/" + idContenido);
    }
}
