package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.model.bd.Critico;
import com.starcritic.dam_proyectspringboot.model.bd.EstadoCertificacion;
import com.starcritic.dam_proyectspringboot.service.CriticoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/criticos")
public class CriticoController {

    private final CriticoService criticoService;

    public CriticoController(CriticoService criticoService) {
        this.criticoService = criticoService;
    }

    @GetMapping
    public List<Critico> listarTodos() {
        return criticoService.listarTodos();
    }

    @GetMapping("/pendientes")
    public List<Critico> pendientes() {
        return criticoService.obtenerCertificacionesPendientes();
    }

    @GetMapping("/estado/{estado}")
    public List<Critico> porEstado(@PathVariable EstadoCertificacion estado) {
        return criticoService.buscarPorEstado(estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Critico> porId(@PathVariable Long id) {
        return criticoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/es-critico")
    public ResponseEntity<Boolean> esCritico(@PathVariable Long id) {
        return ResponseEntity.ok(criticoService.esCritico(id));
    }

    @PostMapping
    public Critico crear(@RequestBody Critico critico) {
        return criticoService.guardar(critico);
    }

    @PostMapping("/{id}/promover")
    public ResponseEntity<Critico> promover(@PathVariable Long id,
                                            @RequestBody(required = false) PromocionRequest body) {
        String certificacion = body != null ? body.certificacion() : null;
        EstadoCertificacion estado = body != null ? body.estado() : null;
        return ResponseEntity.ok(criticoService.promover(id, certificacion, estado));
    }

    public record PromocionRequest(String certificacion, EstadoCertificacion estado) {}
}
