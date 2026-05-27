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

    public static boolean crearListaUsuario(ListaUsuario lista) {
        return ApiClient.get().postObject("/listas-usuario", lista, ListaUsuario.class) != null;
    }

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

    public static void eliminarListaUsuario(int idUsuarioRegistrado, String nombreLista) {
        ApiClient.get().delete("/listas-usuario/usuario/" + idUsuarioRegistrado
                + "/lista/" + ApiClient.enc(nombreLista));
    }
}
