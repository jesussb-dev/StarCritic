package com.starcritic.dam_proyect.data.database;

import com.google.gson.JsonObject;
import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.Contenido;
import com.starcritic.dam_proyect.model.pojo.bd.ContenidoAudiovisual;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.model.pojo.bd.Videojuego;

/**
 * Contenido (películas, series, videojuegos) y su registro/consulta, vía API REST.
 *
 * El "id de API" es el identificador externo del contenido: {@code idOmdb} para
 * audiovisuales y {@code idRawg} para videojuegos. Las altas dejan que el backend
 * asigne el {@code idContenido}.
 *
 * @author Jesús Santos Baquero
 */
public class ContenidoDB {

    public static Contenido obtenerContenido(int idContenido) {
        return ApiClient.get().getObject("/contenidos/" + idContenido, Contenido.class);
    }

    public static TipoContenido obtenerTipoContenido(int idContenido) {
        Contenido contenido = obtenerContenido(idContenido);
        if (contenido == null) {
            return null;
        }
        return contenido.getTipoContenido();
    }

    /** @return el id externo (OMDb o RAWG) del contenido, o {@code null} si no existe. */
    public static String obtenerApiId(int idContenido, TipoContenido tipo) {
        if (tipo == null) {
            return null;
        }
        if (tipo.esAudiovisual()) {
            ContenidoAudiovisual av = ApiClient.get().getObject(
                    "/contenidos-audiovisuales/" + idContenido, ContenidoAudiovisual.class);
            if (av == null) {
                return null;
            }
            return av.getIdOmdb();
        } else {
            Videojuego vj = ApiClient.get().getObject("/videojuegos/" + idContenido, Videojuego.class);
            if (vj == null) {
                return null;
            }
            return String.valueOf(vj.getIdRawg());
        }
    }

    /**
     * Traduce un id externo (OMDb/RAWG) al {@code idContenido} interno.
     *
     * @return el id interno, o {@code 0} si el contenido aún no está en la BD.
     */
    public static int buscarID(String apiId, TipoContenido tipo) {
        if (apiId == null || apiId.isBlank() || tipo == null) {
            return 0;
        }
        if (tipo.esAudiovisual()) {
            ContenidoAudiovisual av = ApiClient.get().getObject(
                    "/contenidos-audiovisuales/omdb/" + ApiClient.enc(apiId), ContenidoAudiovisual.class);
            if (av == null) {
                return 0;
            }
            return av.getIdContenido();
        } else {
            Videojuego vj = ApiClient.get().getObject("/videojuegos/rawg/" + apiId, Videojuego.class);
            if (vj == null) {
                return 0;
            }
            return vj.getIdContenido();
        }
    }

    /**
     * Da de alta el contenido en su subtipo si todavía no existe (idempotente
     * por id externo). No hace nada para un {@link Contenido} sin subtipo.
     */
    public static void registrarContenido(Contenido contenido) {
        if (contenido instanceof ContenidoAudiovisual av) {
            if (av.getIdOmdb() == null) {
                return;
            }
            boolean existe = ApiClient.get().getBoolean(
                    "/contenidos-audiovisuales/omdb/" + ApiClient.enc(av.getIdOmdb()) + "/existe");
            if (!existe) {
                crear("/contenidos-audiovisuales", contenido);
            }
        } else if (contenido instanceof Videojuego vj) {
            boolean existe = ApiClient.get().getBoolean("/videojuegos/rawg/" + vj.getIdRawg() + "/existe");
            if (!existe) {
                crear("/videojuegos", contenido);
            }
        }
    }

    public static double mediaAspectoContenido(int idContenido, int idAspecto, TipoContenido tipo) {
        String sub;
        if (tipo != null && tipo.esAudiovisual()) {
            sub = "/media-audiovisual";
        } else {
            sub = "/media-videojuego";
        }
        Double media = ApiClient.get().getObject(
                "/contenidos/" + idContenido + sub + "?aspecto=" + idAspecto, Double.class);
        if (media == null) {
            return 0d;
        }
        return media;
    }

    /**
     * Alta de un contenido del subtipo indicado ({@code /contenidos-audiovisuales}
     * o {@code /videojuegos}). Gson serializa el POJO y el backend asigna el id.
     *
     * @return {@code true} si la API confirma la creación.
     */
    public static boolean crear(String path, Contenido contenido) {
        return ApiClient.get().postObject(path, contenido, JsonObject.class) != null;
    }
}
