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
     * Validar credenciales de un usuario contra el endpoint de login del backend.
     * @param nombreUsuario el nombre de usuario.
     * @param contrasenha la contraseña en texto plano.
     * @return el usuario si las credenciales son correctas, null si no.
     */
    public static UsuarioRegistrado verificarLogin(String nombreUsuario, String contrasenha) {
        JsonObject body = new JsonObject();
        body.addProperty("nombreUsuario", nombreUsuario);
        body.addProperty("contrasenha", contrasenha);
        return ApiClient.get().postObject("/usuarios/login", body, UsuarioRegistrado.class);
    }

    /**
     * Obtener un usuario por su identificador propio de la base de datos.
     * @param idUsuario el identificador unico del usuario.
     * @return el usuario si existe, en caso contrario null.
     */
    public static UsuarioRegistrado obtenerUsuario(int idUsuario) {
        return ApiClient.get().getObject("/usuarios/" + idUsuario, UsuarioRegistrado.class);
    }

    /**
     * Obtener todos los usuarios registrados en la base de datos.
     * @return un mapa con todos los usuarios indexados por su identificador.
     */
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

    /**
     * Insertar un nuevo usuario registrado.
     * @param usuario el usuario a insertar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean insertarUsuarioRegistrado(UsuarioRegistrado usuario) {
        return ApiClient.get().postObject("/usuarios", usuario, UsuarioRegistrado.class) != null;
    }

    /**
     * Modificar los datos de un usuario existente. La contraseña va nula en
     * el JSON y el backend conserva la existente.
     * @param usuario el usuario con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean modificarUsuarioRegistrado(UsuarioRegistrado usuario) {
        return ApiClient.get().postObject("/usuarios", usuario, UsuarioRegistrado.class) != null;
    }

    /**
     * Comprobar si la contraseña dada coincide con la almacenada para un usuario.
     * @param idUsuario el identificador del usuario.
     * @param contrasenha la contraseña en texto plano a verificar.
     * @return true si la contraseña coincide, false en caso contrario.
     */
    public static boolean verificarContrasenha(int idUsuario, String contrasenha) {
        JsonObject body = new JsonObject();
        body.addProperty("contrasenha", contrasenha);
        Boolean ok = ApiClient.get().postObject(
                "/usuarios/" + idUsuario + "/verificar-contrasenha", body, Boolean.class);
        return Boolean.TRUE.equals(ok);
    }

    /**
     * Cambiar la contraseña de un usuario existente.
     * @param idUsuario el identificador del usuario.
     * @param nuevaContrasenha la nueva contraseña en texto plano.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean modificarContrasenha(int idUsuario, String nuevaContrasenha) {
        JsonObject body = new JsonObject();
        body.addProperty("contrasenha", nuevaContrasenha);
        return ApiClient.get().putOk("/usuarios/" + idUsuario + "/contrasenha", body);
    }

    /**
     * Establecer el estado baneado de un usuario.
     * @param idUsuario el identificador del usuario.
     * @param baneado el nuevo valor del flag baneado.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean banearUsuario(int idUsuario, boolean baneado) {
        JsonObject body = new JsonObject();
        body.addProperty("baneado", baneado);
        return ApiClient.get().patchOk("/usuarios/" + idUsuario + "/baneado", body);
    }

    /**
     * Eliminar un usuario registrado de la base de datos.
     * @param idUsuario el identificador unico del usuario a eliminar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean eliminarUsuario(int idUsuario) {
        return ApiClient.get().delete("/usuarios/" + idUsuario);
    }
}
