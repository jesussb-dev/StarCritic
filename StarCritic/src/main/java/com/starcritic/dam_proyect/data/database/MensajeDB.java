package com.starcritic.dam_proyect.data.database;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.Mensaje;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesMensaje;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mensajería entre usuarios, vía API REST.
 *
 * @author Jesús Santos Baquero
 */
public class MensajeDB {

    public static boolean insertarMensaje(Mensaje mensaje) {
        // Alta: Gson serializa el POJO; el backend asigna el id autogenerado.
        return ApiClient.get().postObject("/mensajes", mensaje, Mensaje.class) != null;
    }

    public static boolean modificarMensaje(Mensaje mensaje) {
        // PUT con el id en la ruta => actualización del mensaje existente.
        return ApiClient.get().putOk("/mensajes/" + mensaje.getIdMensaje(), mensaje);
    }

    public static List<Mensaje> obtenerTodosLosMensajesParaUsuario(int idUsuario) {
        Mensaje[] respuesta = ApiClient.get().getObject("/mensajes/destinatario/" + idUsuario, Mensaje[].class);
        DetallesMensaje detalles = new DetallesMensaje();
        if (respuesta != null) {
            detalles.setMensajes(new ArrayList<>(Arrays.asList(respuesta)));
        } else {
            detalles.setMensajes(new ArrayList<>());
        }
        return detalles.getMensajes();
    }
}
