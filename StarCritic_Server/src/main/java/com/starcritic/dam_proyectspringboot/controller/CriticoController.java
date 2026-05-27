package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.repository.projection.PromocionRequest;
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

/**
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/criticos")
public class CriticoController {

    private final CriticoService criticoService;

    public CriticoController(CriticoService criticoService) {
        this.criticoService = criticoService;
    }
    
    /**
     * Obtener todos los usuarios que son criticos, ya sean verificados o no.
     * @return todos los críticos en formato lista
     */
    @GetMapping
    public List<Critico> listarTodos() {
        return criticoService.listarTodos();
    }
    
    /**
     * Obtener todos los criticos de los cuales están registrado pero sus certificaiones
     * estan pendientes de revisión.
     * @return todos los criticos en estado pendiente en formato lista.
     */
    @GetMapping("/pendientes")
    public List<Critico> pendientes() {
        return criticoService.obtenerCertificacionesPendientes();
    }
    
    /**
     * Obtener los  crítico en un estado determinado.
     * @param estado el estado que poseeran los críticos.
     * @return todos los criticos en ese estado en formato lista.
     */
    @GetMapping("/estado/{estado}")
    public List<Critico> porEstado(@PathVariable EstadoCertificacion estado) {
        return criticoService.buscarPorEstado(estado);
    }
    
    /**
     * Obtener un usuario crítico en concreto.
     * @param id el identificador unico del usuario.
     * @return el usuario en caso de exito y en caso de error una respuesta.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Critico> porId(@PathVariable Long id) {
        return criticoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Comprueba si un usuario es critico, independientemente de su estado.
     * @param id el identificador unico del usuario en la base de datos.
     * @return si el usuario es un crítico o no en booleano, si la operación es
     * errornea una respuesta.
     */
    @GetMapping("/{id}/es-critico")
    public ResponseEntity<Boolean> esCritico(@PathVariable Long id) {
        return ResponseEntity.ok(criticoService.esCritico(id));
    }

    /**
     * Craear un usuario critico directamente.
     * @param critico el objeto critico que se quiere insertar
     * @return devolver el mismo objeto en caso de exito y en caso de error una
     * respuesta.
     */
    @PostMapping
    public Critico crear(@RequestBody Critico critico) {
        return criticoService.guardar(critico);
    }
    
    /**
     * Se promovera un usuario ya creado a critico, creando una instancia en crítico
     * con el mismo identificador.
     * @param id el identificador unico del usuario dentro de la base de datos.
     * @param body los campos propios de crítico dentro de la base de datos.
     * @return  devolvera un objeto crítico en caso de exito, si no una respuesta.
     */
    @PostMapping("/{id}/promover")
    public ResponseEntity<Critico> promover(@PathVariable Long id,@RequestBody(required = false) PromocionRequest body) {
        String certificacion = body != null ? body.certificacion() : null;
        EstadoCertificacion estado = body != null ? body.estado() : null;
        return ResponseEntity.ok(criticoService.promover(id, certificacion, estado));
    }
}
