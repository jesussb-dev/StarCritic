package com.starcritic.dam_proyect.data.database;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.EtiquetaEditorial;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesEtiqueta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Etiquetas editoriales y su relación N:M con contenido, vía API REST.
 *
 * @author Jesús Santos Baquero
 */
public class EtiquetaEditorialDB {

    /**
     * Obtener todas las etiquetas editoriales existentes en la base de datos.
     * @return todas las etiquetas en formato lista.
     */
    public static List<EtiquetaEditorial> obtenerTodas() {
        return lista("/etiquetas");
    }

    /**
     * Obtener las etiquetas asignadas a un contenido.
     * @param idContenido el identificador del contenido.
     * @return las etiquetas del contenido en formato lista.
     */
    public static List<EtiquetaEditorial> obtenerEtiquetasDe(int idContenido) {
        return lista("/etiquetas/contenido/" + idContenido);
    }

    /**
     * Crear una etiqueta editorial. El backend asigna el identificador y se
     * propaga al POJO recibido.
     * @param etiqueta la etiqueta a crear.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean crearEtiqueta(EtiquetaEditorial etiqueta) {
        // Alta: Gson serializa el POJO; el backend asigna el id autogenerado.
        EtiquetaEditorial creada = ApiClient.get().postObject("/etiquetas", etiqueta, EtiquetaEditorial.class);
        if (creada != null) {
            etiqueta.setIdEtiqueta(creada.getIdEtiqueta());
            return true;
        }
        return false;
    }

    /**
     * Renombrar una etiqueta editorial existente.
     * @param idEtiqueta el identificador de la etiqueta a renombrar.
     * @param nuevoNombre el nuevo nombre de la etiqueta.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean renombrarEtiqueta(int idEtiqueta, String nuevoNombre) {
        // El backend hace upsert con POST: enviar id + nuevo nombre actualiza.
        EtiquetaEditorial etiqueta = new EtiquetaEditorial(idEtiqueta, nuevoNombre);
        return ApiClient.get().postObject("/etiquetas", etiqueta, EtiquetaEditorial.class) != null;
    }

    /**
     * Borrar una etiqueta editorial.
     * @param idEtiqueta el identificador de la etiqueta a borrar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean borrarEtiqueta(int idEtiqueta) {
        return ApiClient.get().delete("/etiquetas/" + idEtiqueta);
    }

    /**
     * Asignar una etiqueta a un contenido.
     * @param idContenido el identificador del contenido.
     * @param idEtiqueta el identificador de la etiqueta.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean asignarEtiqueta(int idContenido, int idEtiqueta) {
        return ApiClient.get().postOk("/etiquetas/contenido/" + idContenido + "/etiqueta/" + idEtiqueta, null);
    }

    /**
     * Quitar la asignación de una etiqueta a un contenido.
     * @param idContenido el identificador del contenido.
     * @param idEtiqueta el identificador de la etiqueta.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean desasignarEtiqueta(int idContenido, int idEtiqueta) {
        return ApiClient.get().delete("/etiquetas/contenido/" + idContenido + "/etiqueta/" + idEtiqueta);
    }

    private static List<EtiquetaEditorial> lista(String path) {
        EtiquetaEditorial[] respuesta = ApiClient.get().getObject(path, EtiquetaEditorial[].class);
        DetallesEtiqueta detalles = new DetallesEtiqueta();
        if (respuesta != null) {
            detalles.setEtiquetas(new ArrayList<>(Arrays.asList(respuesta)));
        } else {
            detalles.setEtiquetas(new ArrayList<>());
        }
        return detalles.getEtiquetas();
    }
}
