package com.starcritic.dam_proyect.data.database;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.data.cloudfare.CloudeClient;
import com.starcritic.dam_proyect.model.pojo.bd.Contenido;
import com.starcritic.dam_proyect.model.pojo.bd.ContenidoAudiovisual;
import com.starcritic.dam_proyect.model.pojo.bd.Origen;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.model.pojo.bd.Videojuego;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesContenidoAudiovisual;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesVideojuego;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Operaciones de administración del catálogo, vía API REST.
 *
 * El "borrado" admin es por defecto soft delete (oculto = TRUE). El hard delete
 * solo se permite sobre contenido LOCAL; la integridad referencial (críticas,
 * listas, visitas) la garantizan las FK del backend: si el contenido está
 * referenciado, la API rechazará el borrado.
 *
 * @author Jesús Santos Baquero
 */
public class AdminContenidoDB {

    public static List<Contenido> obtenerCatalogoCompleto() {
        List<Contenido> catalogo = new ArrayList<>();

        ContenidoAudiovisual[] avs = ApiClient.get().getObject("/contenidos-audiovisuales", ContenidoAudiovisual[].class);
        DetallesContenidoAudiovisual detallesAv = new DetallesContenidoAudiovisual();
        if (avs != null) {
            detallesAv.setContenidosAudiovisuales(new ArrayList<>(Arrays.asList(avs)));
        } else {
            detallesAv.setContenidosAudiovisuales(new ArrayList<>());
        }

        Videojuego[] vjs = ApiClient.get().getObject("/videojuegos", Videojuego[].class);
        DetallesVideojuego detallesVj = new DetallesVideojuego();
        if (vjs != null) {
            detallesVj.setVideojuegos(new ArrayList<>(Arrays.asList(vjs)));
        } else {
            detallesVj.setVideojuegos(new ArrayList<>());
        }

        catalogo.addAll(detallesAv.getContenidosAudiovisuales());
        catalogo.addAll(detallesVj.getVideojuegos());
        catalogo.sort(Comparator.comparingInt(Contenido::getIdContenido));
        return catalogo;
    }

    public static boolean actualizarOculto(int idContenido, boolean oculto) {
        return ApiClient.get().patchOk("/contenidos/" + idContenido + "/oculto?valor=" + oculto, null);
    }

    public static boolean actualizarDestacado(int idContenido, boolean destacado) {
        return ApiClient.get().patchOk("/contenidos/" + idContenido + "/destacado?valor=" + destacado, null);
    }

    /**
     * @param posterFile puede ser null (sin póster)
     */
    public static boolean crearContenidoLocal(CloudeClient cloud,
            Contenido contenido, File posterFile) {
        if (contenido.getTitulo() == null || contenido.getTitulo().isBlank()
                || contenido.getTipoContenido() == null) {
            System.err.println("Contenido LOCAL requiere titulo y tipo");
            return false;
        }
        contenido.setOrigen(Origen.LOCAL);
        if (contenido.getSinopsis() == null) {
            contenido.setSinopsis("");
        }
        if (posterFile != null && cloud != null) {
            try {
                contenido.setPosterKey(cloud.subirArchivo(posterFile, detectarContentType(posterFile)));
            } catch (Exception ex) {
                System.err.println("Error subiendo poster a R2");
                ex.printStackTrace();
                return false;
            }
        }
        if (contenido.getPosterKey() == null) {
            contenido.setPosterKey("");
        }
        return ContenidoDB.crear(subtipoPath(contenido), contenido);
    }

    /**
     * Actualiza título, sinopsis, fecha y opcionalmente el póster de un
     * contenido LOCAL.
     */
    public static boolean actualizarContenidoLocal(CloudeClient cloud,
            Contenido contenido, File nuevoPoster) {
        if (contenido.getOrigen() != Origen.LOCAL) {
            System.err.println("Solo se puede editar contenido LOCAL con este metodo");
            return false;
        }
        if (nuevoPoster != null && cloud != null) {
            try {
                contenido.setPosterKey(cloud.subirArchivo(nuevoPoster, detectarContentType(nuevoPoster)));
            } catch (Exception ex) {
                System.err.println("Error subiendo nuevo poster a R2");
                ex.printStackTrace();
                return false;
            }
        }
        // POST con idContenido presente => actualización (upsert) en el backend.
        return ApiClient.get().postObject(subtipoPath(contenido), contenido, JsonObject.class) != null;
    }

    public static boolean softDelete(int idContenido) {
        return actualizarOculto(idContenido, true);
    }

    public static boolean hardDelete(int idContenido, CloudeClient cloud) {
        if (!puedeHardDelete(idContenido)) {
            System.err.println("No se puede hard-delete el contenido " + idContenido
                    + ": no es LOCAL. Use softDelete.");
            return false;
        }
        String posterKey = obtenerPosterKey(idContenido);
        boolean ok = ApiClient.get().delete("/contenidos/" + idContenido);
        if (ok && cloud != null && posterKey != null && !posterKey.isBlank()) {
            try {
                cloud.eliminarArchivo(posterKey);
            } catch (Exception ex) {
                System.err.println("Contenido eliminado de BD pero no se pudo borrar el poster de R2: " + ex.getMessage());
            }
        }
        return ok;
    }

    public static boolean puedeHardDelete(int idContenido) {
        JsonObject contenido = obtenerContenidoJson(idContenido);
        return contenido != null
                && contenido.has("origen")
                && "LOCAL".equals(contenido.get("origen").getAsString());
    }

    // ===================== helpers ===================== //

    private static String subtipoPath(Contenido contenido) {
        return contenido.getTipoContenido() == TipoContenido.VIDEOJUEGO
                ? "/videojuegos" : "/contenidos-audiovisuales";
    }

    private static JsonObject obtenerContenidoJson(int idContenido) {
        JsonElement resp = ApiClient.get().getJson("/contenidos/" + idContenido);
        return (resp != null && resp.isJsonObject()) ? resp.getAsJsonObject() : null;
    }

    private static String obtenerPosterKey(int idContenido) {
        JsonObject obj = obtenerContenidoJson(idContenido);
        if (obj != null && obj.has("posterKey") && !obj.get("posterKey").isJsonNull()) {
            return obj.get("posterKey").getAsString();
        }
        return null;
    }

    private static String detectarContentType(File f) {
        String name = f.getName().toLowerCase();
        if (name.endsWith(".png")) {
            return "image/png";
        }
        if (name.endsWith(".webp")) {
            return "image/webp";
        }
        if (name.endsWith(".gif")) {
            return "image/gif";
        }
        return "image/jpeg";
    }
}
