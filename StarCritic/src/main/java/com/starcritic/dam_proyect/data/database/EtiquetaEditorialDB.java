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

    public static List<EtiquetaEditorial> obtenerTodas() {
        return lista("/etiquetas");
    }

    public static List<EtiquetaEditorial> obtenerEtiquetasDe(int idContenido) {
        return lista("/etiquetas/contenido/" + idContenido);
    }

    public static boolean crearEtiqueta(EtiquetaEditorial etiqueta) {
        // Alta: Gson serializa el POJO; el backend asigna el id autogenerado.
        EtiquetaEditorial creada = ApiClient.get().postObject("/etiquetas", etiqueta, EtiquetaEditorial.class);
        if (creada != null) {
            etiqueta.setIdEtiqueta(creada.getIdEtiqueta());
            return true;
        }
        return false;
    }

    public static boolean renombrarEtiqueta(int idEtiqueta, String nuevoNombre) {
        // El backend hace upsert con POST: enviar id + nuevo nombre actualiza.
        EtiquetaEditorial etiqueta = new EtiquetaEditorial(idEtiqueta, nuevoNombre);
        return ApiClient.get().postObject("/etiquetas", etiqueta, EtiquetaEditorial.class) != null;
    }

    public static boolean borrarEtiqueta(int idEtiqueta) {
        return ApiClient.get().delete("/etiquetas/" + idEtiqueta);
    }

    public static boolean asignarEtiqueta(int idContenido, int idEtiqueta) {
        return ApiClient.get().postOk("/etiquetas/contenido/" + idContenido + "/etiqueta/" + idEtiqueta, null);
    }

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
