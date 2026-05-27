package com.starcritic.dam_proyect.data.database;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.ListaUsuario;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesListaUsuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Listas (colecciones) de un usuario, vía API REST.
 *
 * @author Jesús Santos Baquero
 */
public class ListaUsuarioDB {

    /**
     * Crear una lista para un usuario.
     * @param lista la lista a crear.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean crearListaUsuario(ListaUsuario lista) {
        return ApiClient.get().postObject("/listas-usuario", lista, ListaUsuario.class) != null;
    }

    /**
     * Obtener todas las listas pertenecientes a un usuario.
     * @param idUsuarioRegistrado el identificador del usuario propietario.
     * @return las listas del usuario en formato lista.
     */
    public static List<ListaUsuario> obtenerListasUsuario(int idUsuarioRegistrado) {
        ListaUsuario[] respuesta = ApiClient.get().getObject(
                "/listas-usuario/usuario/" + idUsuarioRegistrado, ListaUsuario[].class);
        DetallesListaUsuario detalles = new DetallesListaUsuario();
        if (respuesta != null) {
            detalles.setListas(new ArrayList<>(Arrays.asList(respuesta)));
        } else {
            detalles.setListas(new ArrayList<>());
        }
        return detalles.getListas();
    }

    /**
     * Eliminar una lista de un usuario.
     * @param idUsuarioRegistrado el identificador del usuario propietario.
     * @param nombreLista el nombre de la lista a eliminar.
     */
    public static void eliminarListaUsuario(int idUsuarioRegistrado, String nombreLista) {
        ApiClient.get().delete("/listas-usuario/usuario/" + idUsuarioRegistrado
                + "/lista/" + ApiClient.enc(nombreLista));
    }
}
