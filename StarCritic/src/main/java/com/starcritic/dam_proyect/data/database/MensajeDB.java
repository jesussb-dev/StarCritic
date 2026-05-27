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

    /**
     * Insertar un mensaje en la base de datos. Gson serializa el POJO y el
     * backend asigna el identificador autogenerado.
     * @param mensaje el mensaje a insertar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean insertarMensaje(Mensaje mensaje) {
        return ApiClient.get().postObject("/mensajes", mensaje, Mensaje.class) != null;
    }

    /**
     * Modificar un mensaje existente. El PUT con el id en la ruta actualiza
     * el mensaje en la base de datos.
     * @param mensaje el mensaje con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean modificarMensaje(Mensaje mensaje) {
        return ApiClient.get().putOk("/mensajes/" + mensaje.getIdMensaje(), mensaje);
    }

    /**
     * Obtener todos los mensajes recibidos por un usuario.
     * @param idUsuario el identificador del usuario destinatario.
     * @return los mensajes del usuario en formato lista.
     */
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
