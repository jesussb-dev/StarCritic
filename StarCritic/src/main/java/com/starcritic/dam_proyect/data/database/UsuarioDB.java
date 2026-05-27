package com.starcritic.dam_proyect.data.database;

import com.google.gson.JsonObject;
import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.UsuarioRegistrado;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesUsuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Usuarios registrados, vía API REST. El cifrado de contraseñas (BCrypt) lo
 * realiza ahora el backend: el cliente envía siempre texto plano y nunca recibe
 * el hash de vuelta (el campo {@code contrasenha} es de solo escritura).
 *
 * @author Jesús Santos Baquero
 */
public class UsuarioDB {

    /**
     * Valida credenciales contra {@code POST /usuarios/login}.
     *
     * @return el usuario si las credenciales son correctas; {@code null} si no
     *         (la API responde 401 y {@code postObject} lo traduce a null).
     */
    public static UsuarioRegistrado verificarLogin(String nombreUsuario, String contrasenha) {
        JsonObject body = new JsonObject();
        body.addProperty("nombreUsuario", nombreUsuario);
        body.addProperty("contrasenha", contrasenha);
        return ApiClient.get().postObject("/usuarios/login", body, UsuarioRegistrado.class);
    }

    public static UsuarioRegistrado obtenerUsuario(int idUsuario) {
        return ApiClient.get().getObject("/usuarios/" + idUsuario, UsuarioRegistrado.class);
    }

    /** @return todos los usuarios indexados por su id. */
    public static Map<Integer, UsuarioRegistrado> obtenerTodosLosUsuarios() {
        Map<Integer, UsuarioRegistrado> usuarios = new LinkedHashMap<>();
        UsuarioRegistrado[] respuesta = ApiClient.get().getObject("/usuarios", UsuarioRegistrado[].class);
        DetallesUsuario detalles = new DetallesUsuario();
        if (respuesta != null) {
            detalles.setUsuarios(new ArrayList<>(Arrays.asList(respuesta)));
        } else {
            detalles.setUsuarios(new ArrayList<>());
        }
        for (UsuarioRegistrado u : detalles.getUsuarios()) {
            usuarios.put(u.getIdUsuario(), u);
        }
        return usuarios;
    }

    public static boolean insertarUsuarioRegistrado(UsuarioRegistrado usuario) {
        return ApiClient.get().postObject("/usuarios", usuario, UsuarioRegistrado.class) != null;
    }

    public static boolean modificarUsuarioRegistrado(UsuarioRegistrado usuario) {
        // POST con id presente => upsert/actualización. La contraseña va nula en
        // el JSON (Gson omite nulls) y el backend conserva la existente.
        return ApiClient.get().postObject("/usuarios", usuario, UsuarioRegistrado.class) != null;
    }

    public static boolean verificarContrasenha(int idUsuario, String contrasenha) {
        JsonObject body = new JsonObject();
        body.addProperty("contrasenha", contrasenha);
        Boolean ok = ApiClient.get().postObject(
                "/usuarios/" + idUsuario + "/verificar-contrasenha", body, Boolean.class);
        return Boolean.TRUE.equals(ok);
    }

    public static boolean modificarContrasenha(int idUsuario, String nuevaContrasenha) {
        JsonObject body = new JsonObject();
        body.addProperty("contrasenha", nuevaContrasenha);
        return ApiClient.get().putOk("/usuarios/" + idUsuario + "/contrasenha", body);
    }

    public static boolean banearUsuario(int idUsuario, boolean baneado) {
        JsonObject body = new JsonObject();
        body.addProperty("baneado", baneado);
        return ApiClient.get().patchOk("/usuarios/" + idUsuario + "/baneado", body);
    }

    public static boolean eliminarUsuario(int idUsuario) {
        return ApiClient.get().delete("/usuarios/" + idUsuario);
    }
}
