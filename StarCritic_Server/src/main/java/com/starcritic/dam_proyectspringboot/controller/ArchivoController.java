package com.starcritic.dam_proyectspringboot.controller;

import com.starcritic.dam_proyectspringboot.service.R2Service;
import com.starcritic.dam_proyectspringboot.service.R2Service.Bucket;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * Endpoints de almacenamiento en Cloudflare R2. Centralizan en el servidor la
 * subida, firma de URLs temporales y borrado de objetos, de modo que el cliente
 * no necesita credenciales de R2.
 * @author Jesús Santos Baquero
 */
@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {

    private final R2Service r2Service;

    public ArchivoController(R2Service r2Service) {
        this.r2Service = r2Service;
    }

    /** Traducir el segmento de ruta al bucket lógico. */
    private Bucket resolverBucket(String bucket) {
        return switch (bucket) {
            case "certificaciones" -> Bucket.CERTIFICACIONES;
            case "contenido-local" -> Bucket.CONTENIDO_LOCAL;
            default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Bucket desconocido: " + bucket);
        };
    }

    /**
     * Subir un archivo al bucket indicado.
     * @param bucket el bucket lógico ("certificaciones" o "contenido-local").
     * @param archivo el fichero a subir (campo multipart "archivo").
     * @return la clave con la que el objeto queda almacenado.
     */
    @PostMapping(value = "/{bucket}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String subir(@PathVariable String bucket,
                        @RequestParam("archivo") MultipartFile archivo) {
        return r2Service.subir(archivo, resolverBucket(bucket));
    }

    /**
     * Descargar el contenido completo de un objeto del bucket.
     * @param bucket el bucket lógico ("certificaciones" o "contenido-local").
     * @param key la clave del objeto.
     * @return los bytes del objeto como flujo binario.
     */
    @GetMapping(value = "/{bucket}/contenido", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> descargar(@PathVariable String bucket,
                                           @RequestParam String key) {
        byte[] datos = r2Service.descargar(resolverBucket(bucket), key);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(datos);
    }

    /**
     * Eliminar un objeto del bucket indicado.
     * @param bucket el bucket lógico ("certificaciones" o "contenido-local").
     * @param key la clave del objeto a eliminar.
     * @return 204 si la operación se completa.
     */
    @DeleteMapping("/{bucket}")
    public ResponseEntity<Void> eliminar(@PathVariable String bucket,
                                        @RequestParam String key) {
        r2Service.eliminar(resolverBucket(bucket), key);
        return ResponseEntity.noContent().build();
    }
}
