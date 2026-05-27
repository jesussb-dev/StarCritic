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

    /**
     * Obtener un contenido por su identificador propio de la base de datos.
     * @param idContenido el identificador unico en la base de datos.
     * @return el contenido si existe, en caso contrario null.
     */
    public static Contenido obtenerContenido(int idContenido) {
        return ApiClient.get().getObject("/contenidos/" + idContenido, Contenido.class);
    }

    /**
     * Obtener el tipo de un contenido por su identificador.
     * @param idContenido el identificador unico en la base de datos.
     * @return el {@link TipoContenido} del contenido, o null si no existe.
     */
    public static TipoContenido obtenerTipoContenido(int idContenido) {
        Contenido contenido = obtenerContenido(idContenido);
        if (contenido == null) {
            return null;
        }
        return contenido.getTipoContenido();
    }

    /**
     * Obtener el id externo (OMDb o RAWG) de un contenido.
     * @param idContenido el identificador interno del contenido.
     * @param tipo el tipo del contenido para saber a que subtipo consultar.
     * @return el id externo del contenido, o null si no existe.
     */
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
     * Traducir un id externo (OMDb/RAWG) al identificador interno de la base de datos.
     * @param apiId el id externo del contenido (OMDb para audiovisual, RAWG para videojuego).
     * @param tipo el tipo del contenido para saber a que subtipo consultar.
     * @return el id interno, o 0 si el contenido aún no está en la base de datos.
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
     * Dar de alta un contenido en su subtipo si todavia no existe. Operación
     * idempotente por id externo: no hace nada si el contenido ya existe ni
     * tampoco para un {@link Contenido} sin subtipo.
     * @param contenido el contenido a registrar.
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

    /**
     * Calcular la media de puntuaciones de un aspecto para un contenido concreto.
     * @param idContenido el identificador del contenido.
     * @param idAspecto el identificador del aspecto del que se calculara la media.
     * @param tipo el tipo del contenido para saber a que subtipo consultar.
     * @return la media de puntuaciones del aspecto, 0 si no hay datos.
     */
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
     * Dar de alta un contenido del subtipo indicado. Gson serializa el POJO y
     * el backend asigna el identificador.
     * @param path la ruta del subtipo ({@code /contenidos-audiovisuales} o {@code /videojuegos}).
     * @param contenido el contenido a crear.
     * @return true si la API confirma la creación, false en caso contrario.
     */
    public static boolean crear(String path, Contenido contenido) {
        return ApiClient.get().postObject(path, contenido, JsonObject.class) != null;
    }
}
