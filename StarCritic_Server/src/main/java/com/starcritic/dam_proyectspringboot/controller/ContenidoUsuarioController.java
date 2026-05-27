package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.service.ContenidoUsuarioService;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contenido-usuario")
public class ContenidoUsuarioController {

    private final ContenidoUsuarioService contenidoUsuarioService;

    public ContenidoUsuarioController(ContenidoUsuarioService contenidoUsuarioService) {
        this.contenidoUsuarioService = contenidoUsuarioService;
    }

    /**
     * Registra una visita; si no se indica fecha se usa la de hoy.
     * @param idUsuario el identificador del usuario en la BD
     * @param idContenido el identificador del contenido en la BD
     * @param fecha la fecha de creación o actualizacición
     * @return nada, solo en caso de error devolvera para informar de este
     */
    @PostMapping("/{idUsuario}/{idContenido}/visita")
    public ResponseEntity<Void> registrarVisita(@PathVariable Long idUsuario,@PathVariable Long idContenido,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        contenidoUsuarioService.registrarVisita(idUsuario, idContenido,
                fecha != null ? fecha : LocalDate.now());
        return ResponseEntity.noContent().build();
    }
}
