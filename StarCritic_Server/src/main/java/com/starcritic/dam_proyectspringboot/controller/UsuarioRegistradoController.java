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

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRegistradoController {

    private final UsuarioRegistradoService usuarioRegistradoService;

    public UsuarioRegistradoController(UsuarioRegistradoService usuarioRegistradoService) {
        this.usuarioRegistradoService = usuarioRegistradoService;
    }

    @GetMapping
    public List<UsuarioRegistrado> listarTodos() {
        return usuarioRegistradoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRegistrado> porId(@PathVariable Long id) {
        return usuarioRegistradoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UsuarioRegistrado crear(@RequestBody UsuarioRegistrado usuario) {
        return usuarioRegistradoService.guardar(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioRegistradoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioRegistrado> login(@RequestBody LoginRequest credenciales) {
        return usuarioRegistradoService.login(credenciales.nombreUsuario(), credenciales.contrasenha())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PostMapping("/{id}/verificar-contrasenha")
    public boolean verificarContrasenha(@PathVariable Long id, @RequestBody ContrasenhaRequest body) {
        return usuarioRegistradoService.verificarContrasenha(id, body.contrasenha());
    }

    @PutMapping("/{id}/contrasenha")
    public ResponseEntity<Void> cambiarContrasenha(@PathVariable Long id, @RequestBody ContrasenhaRequest body) {
        boolean ok = usuarioRegistradoService.cambiarContrasenha(id, body.contrasenha());
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/baneado")
    public ResponseEntity<Void> setBaneado(@PathVariable Long id, @RequestBody BaneadoRequest body) {
        boolean ok = usuarioRegistradoService.setBaneado(id, body.baneado());
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    public record LoginRequest(String nombreUsuario, String contrasenha) {}

    public record ContrasenhaRequest(String contrasenha) {}

    public record BaneadoRequest(boolean baneado) {}
}
