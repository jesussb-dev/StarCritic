package com.starcritic.dam_proyect.data.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.Critica;
import com.starcritic.dam_proyect.model.pojo.bd.CriticaAudiovisual;
import com.starcritic.dam_proyect.model.pojo.bd.CriticaVideojuego;
import com.starcritic.dam_proyect.model.pojo.bd.Roles;
import java.util.ArrayList;
import java.util.List;

/**
 * Críticas (reseñas) audiovisuales y de videojuego, vía API REST.
 *
 * La API devuelve entidades {@code CriticaAudiovisual}/{@code CriticaVideojuego}
 * con el usuario anidado; aquí se aplanan al POJO {@link Critica} que esperan
 * las vistas (puntuación, descripción, autor y rol).
 *
 * @author Jesús Santos Baquero
 */
public class CriticaDB {

    public static List<Critica> obtenerCriticasAudiovisualPorAspecto(int idAspecto, int idContenidoAudiovisual) {
        return parsearCriticas(ApiClient.get().getJson(
                "/criticas/audiovisual/aspecto/" + idAspecto + "/contenido/" + idContenidoAudiovisual));
    }

    public static List<Critica> obtenerCriticasVideojuegoPorAspecto(int idAspecto, int idVideojuego) {
        return parsearCriticas(ApiClient.get().getJson(
                "/criticas/videojuego/aspecto/" + idAspecto + "/videojuego/" + idVideojuego));
    }

    public static List<Critica> obtenerCriticasAudiovisualPorUsuario(int idAspecto, int idCritico) {
        return parsearCriticas(ApiClient.get().getJson(
                "/criticas/audiovisual/aspecto/" + idAspecto + "/usuario/" + idCritico));
    }

    public static List<Critica> obtenerCriticasVideojuegoPorUsuario(int idAspecto, int idUsuario) {
        return parsearCriticas(ApiClient.get().getJson(
                "/criticas/videojuego/aspecto/" + idAspecto + "/usuario/" + idUsuario));
    }

    public static boolean registrarCriticaAudiovisual(CriticaAudiovisual critica) {
        JsonObject body = cuerpoBase(critica.getPuntuacion(), critica.getDescripcion(),
                critica.getIdUsuarioRegistrado(), critica.getIdAspecto());
        body.add("contenidoAudiovisual", refContenido(critica.getIdContenidoAudiovisual()));
        return crear("/criticas-audiovisuales", body, critica);
    }

    public static boolean registrarCriticaVideojuego(CriticaVideojuego critica) {
        JsonObject body = cuerpoBase(critica.getPuntuacion(), critica.getDescripcion(),
                critica.getIdUsuarioRegistrado(), critica.getIdAspecto());
        body.add("videojuego", refContenido(critica.getIdVideojuego()));
        return crear("/criticas-videojuegos", body, critica);
    }

    public static void eliminarCritica(int idCritica) {
        ApiClient.get().delete("/criticas/" + idCritica);
    }

    public static boolean esCriticaUsuario(int idUsuario, int idCritica) {
        return ApiClient.get().getBoolean("/criticas/" + idCritica + "/es-de-usuario/" + idUsuario);
    }

    // ===================== helpers ===================== //

    private static boolean crear(String path, JsonObject body, Critica destino) {
        JsonObject creada = ApiClient.get().postObject(path, body, JsonObject.class);
        if (creada != null && creada.has("idCritica") && !creada.get("idCritica").isJsonNull()) {
            destino.setIdCritica(creada.get("idCritica").getAsInt());
            return true;
        }
        return false;
    }

    private static JsonObject cuerpoBase(int puntuacion, String descripcion, int idUsuario, int idAspecto) {
        JsonObject body = new JsonObject();
        body.addProperty("puntuacion", puntuacion);
        body.addProperty("descripcion", descripcion);
        JsonObject usuario = new JsonObject();
        usuario.addProperty("idUsuario", idUsuario);
        body.add("usuarioRegistrado", usuario);
        JsonObject aspecto = new JsonObject();
        aspecto.addProperty("idAspecto", idAspecto);
        body.add("aspecto", aspecto);
        return body;
    }

    private static JsonObject refContenido(int idContenido) {
        JsonObject ref = new JsonObject();
        ref.addProperty("idContenido", idContenido);
        return ref;
    }

    private static List<Critica> parsearCriticas(JsonElement resp) {
        List<Critica> criticas = new ArrayList<>();
        if (resp == null || !resp.isJsonArray()) {
            return criticas;
        }
        JsonArray arr = resp.getAsJsonArray();
        for (JsonElement el : arr) {
            JsonObject obj = el.getAsJsonObject();
            int puntuacion = obj.get("puntuacion").getAsInt();
            String descripcion = obj.has("descripcion") && !obj.get("descripcion").isJsonNull()
                    ? obj.get("descripcion").getAsString() : null;

            int idUsuario = 0;
            String nombreUsuario = null;
            Roles rol = null;
            JsonObject usuario = obj.has("usuarioRegistrado") && obj.get("usuarioRegistrado").isJsonObject()
                    ? obj.getAsJsonObject("usuarioRegistrado") : null;
            if (usuario != null) {
                if (usuario.has("idUsuario") && !usuario.get("idUsuario").isJsonNull()) {
                    idUsuario = usuario.get("idUsuario").getAsInt();
                }
                if (usuario.has("nombreUsuario") && !usuario.get("nombreUsuario").isJsonNull()) {
                    nombreUsuario = usuario.get("nombreUsuario").getAsString();
                }
                if (usuario.has("rol") && !usuario.get("rol").isJsonNull()) {
                    rol = Roles.valueOf(usuario.get("rol").getAsString());
                }
            }

            Critica critica = new Critica(puntuacion, descripcion, idUsuario, nombreUsuario, rol);
            if (obj.has("idCritica") && !obj.get("idCritica").isJsonNull()) {
                critica.setIdCritica(obj.get("idCritica").getAsInt());
            }
            criticas.add(critica);
        }
        return criticas;
    }
}
