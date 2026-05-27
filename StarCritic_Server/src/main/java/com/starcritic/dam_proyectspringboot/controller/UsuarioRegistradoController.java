package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.UsuarioRegistrado;
import com.starcritic.dam_proyectspringboot.service.UsuarioRegistradoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRegistradoController {

    private final UsuarioRegistradoService usuarioRegistradoService;

    public UsuarioRegistradoController(UsuarioRegistradoService usuarioRegistradoService) {
        this.usuarioRegistradoService = usuarioRegistradoService;
    }

    /**
     * Obtener todos los usuarios registrados existentes en la base de datos.
     * @return todos los usuarios en formato lista.
     */
    @GetMapping
    public List<UsuarioRegistrado> listarTodos() {
        return usuarioRegistradoService.listarTodos();
    }

    /**
     * Obtener un usuario registrado a través de su identificador en la base de datos.
     * @param id el identificador unico dentro de la base de datos.
     * @return en caso de exito devolvera el usuario, en caso de error una
     * respuesta de error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRegistrado> porId(@PathVariable Long id) {
        return usuarioRegistradoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crear un usuario registrado en la base de datos.
     * @param usuario el objeto usuario que se desea insertar.
     * @return el usuario guardado si la operación fue exitosa.
     */
    @PostMapping
    public UsuarioRegistrado crear(@RequestBody UsuarioRegistrado usuario) {
        return usuarioRegistradoService.guardar(usuario);
    }

    /**
     * Eliminar un usuario registrado de la base de datos.
     * @param id el identificador unico dentro de la base de datos del usuario a eliminar.
     * @return no devolvera nada en caso de exito, en caso de error la respuesta del error.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioRegistradoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza el inicio de sesión de un usuario verificando sus credenciales.
     * @param credenciales el nombre de usuario y la contraseña.
     * @return el usuario si las credenciales son correctas, 401 si no lo son.
     */
    @PostMapping("/login")
    public ResponseEntity<UsuarioRegistrado> login(@RequestBody LoginRequest credenciales) {
        return usuarioRegistradoService.login(credenciales.nombreUsuario(), credenciales.contrasenha())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    /**
     * Verifica si la contraseña dada coincide con la almacenada para un usuario.
     * @param id el identificador unico del usuario en la base de datos.
     * @param body el cuerpo con la contraseña a verificar.
     * @return true si la contraseña coincide, false en caso contrario.
     */
    @PostMapping("/{id}/verificar-contrasenha")
    public boolean verificarContrasenha(@PathVariable Long id, @RequestBody ContrasenhaRequest body) {
        return usuarioRegistradoService.verificarContrasenha(id, body.contrasenha());
    }

    /**
     * Cambia la contraseña de un usuario existente.
     * @param id el identificador unico del usuario en la base de datos.
     * @param body el cuerpo con la nueva contraseña.
     * @return no devolvera nada en caso de exito, 404 si el usuario no existe.
     */
    @PutMapping("/{id}/contrasenha")
    public ResponseEntity<Void> cambiarContrasenha(@PathVariable Long id, @RequestBody ContrasenhaRequest body) {
        boolean ok = usuarioRegistradoService.cambiarContrasenha(id, body.contrasenha());
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Establece el estado baneado de un usuario.
     * @param id el identificador unico del usuario en la base de datos.
     * @param body el cuerpo con el nuevo estado baneado.
     * @return no devolvera nada en caso de exito, 404 si el usuario no existe.
     */
    @PatchMapping("/{id}/baneado")
    public ResponseEntity<Void> setBaneado(@PathVariable Long id, @RequestBody BaneadoRequest body) {
        boolean ok = usuarioRegistradoService.setBaneado(id, body.baneado());
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Cuerpo de la peticion de login (nombre de usuario y contraseña).
     */
    public record LoginRequest(String nombreUsuario, String contrasenha) {}

    /**
     * Cuerpo de la peticion que transporta una contraseña.
     */
    public record ContrasenhaRequest(String contrasenha) {}

    /**
     * Cuerpo de la peticion que transporta el nuevo estado baneado.
     */
    public record BaneadoRequest(boolean baneado) {}
}
