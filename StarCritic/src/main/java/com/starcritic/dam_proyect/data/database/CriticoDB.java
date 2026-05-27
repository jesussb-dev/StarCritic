package com.starcritic.dam_proyect.data.database;

import com.google.gson.JsonObject;
import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.Critico;
import com.starcritic.dam_proyect.model.pojo.bd.EstadoCertificacion;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesCritico;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Críticos (usuarios con certificación), vía API REST.
 *
 * @author Jesús Santos Baquero
 */
public class CriticoDB {

    public static HashMap<Integer, Critico> getUsuariosConCertificacionesPendientes() {
        HashMap<Integer, Critico> usuarios = new HashMap<>();
        Critico[] respuesta = ApiClient.get().getObject("/criticos/pendientes", Critico[].class);
        DetallesCritico detalles = new DetallesCritico();
        if (respuesta != null) {
            detalles.setCriticos(new ArrayList<>(Arrays.asList(respuesta)));
        } else {
            detalles.setCriticos(new ArrayList<>());
        }
        for (Critico c : detalles.getCriticos()) {
            usuarios.put(c.getIdUsuario(), c);
        }
        return usuarios;
    }

    public static void anhadirCritico(int idUsuario, String certificacion, EstadoCertificacion estado) {
        // Promueve un usuario existente a crítico: el backend solo añade la fila
        // de la subclase critico (herencia JOINED) conservando los datos del
        // usuario. La contraseña no viaja en el JSON.
        JsonObject body = new JsonObject();
        String cert = "";
        if (certificacion != null) {
            cert = certificacion;
        }
        body.addProperty("certificacion", cert);
        if (estado != null) {
            body.addProperty("estado", estado.name());
        }
        ApiClient.get().postObject("/criticos/" + idUsuario + "/promover", body, Critico.class);
    }

    public static Critico obtenerCritico(int idUsuario) {
        return ApiClient.get().getObject("/criticos/" + idUsuario, Critico.class);
    }

    public static boolean esCritico(int idUsuario) {
        return ApiClient.get().getBoolean("/criticos/" + idUsuario + "/es-critico");
    }

    public static boolean modificarCritico(Critico critico) {
        // POST con id presente => actualiza usuario_registrado + critico. La
        // contraseña va nula en el JSON y el backend conserva la existente.
        return ApiClient.get().postObject("/criticos", critico, Critico.class) != null;
    }
}
