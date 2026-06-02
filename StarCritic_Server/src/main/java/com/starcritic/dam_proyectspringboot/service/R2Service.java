package com.starcritic.dam_proyectspringboot.service;

import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Lógica de almacenamiento en Cloudflare R2 (API compatible con S3). Permite
 * subir, descargar y eliminar objetos en los buckets de certificaciones y
 * contenido local. Esta lógica vive solo en el servidor; el cliente la consume
 * vía REST.
 * @author Jesús Santos Baquero
 */
@Service
public class R2Service {

    /** Bucket lógico, resuelto al nombre real configurado en application.properties. */
    public enum Bucket { CERTIFICACIONES, CONTENIDO_LOCAL }

    private final S3Client s3Client;

    @Value("${cloudflare.bucket.certificaciones}")
    private String bucketCertificaciones;
    @Value("${cloudflare.bucket.contenido-local}")
    private String bucketContenidoLocal;

    public R2Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    private String nombreBucket(Bucket bucket) {
        return switch (bucket) {
            case CERTIFICACIONES -> bucketCertificaciones;
            case CONTENIDO_LOCAL -> bucketContenidoLocal;
        };
    }

    /**
     * Subir un archivo al bucket indicado. La clave se prefija con un UUID
     * aleatorio para evitar colisiones.
     * @param archivo el fichero recibido en la petición multipart.
     * @param bucket el bucket lógico destino.
     * @return la clave (key) con la que el objeto queda almacenado.
     */
    public String subir(MultipartFile archivo, Bucket bucket) {
        String nombreOriginal = archivo.getOriginalFilename();
        if (nombreOriginal == null || nombreOriginal.isBlank()) {
            nombreOriginal = "archivo";
        }
        String key = UUID.randomUUID() + "_" + nombreOriginal;
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(nombreBucket(bucket))
                .key(key)
                .contentType(archivo.getContentType())
                .build();
        try {
            // fromBytes da al SDK un cuerpo repetible: el cliente HTTP necesita
            // re-leerlo (firma/reintentos) y el InputStream crudo del MultipartFile
            // no soporta mark/reset.
            s3Client.putObject(request, RequestBody.fromBytes(archivo.getBytes()));
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No se pudo leer el archivo recibido", ex);
        }
        return key;
    }

    /**
     * Descargar el contenido completo de un objeto del bucket indicado.
     * @param bucket el bucket lógico donde reside el objeto.
     * @param key la clave del objeto.
     * @return los bytes del objeto.
     */
    public byte[] descargar(Bucket bucket, String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(nombreBucket(bucket))
                .key(key)
                .build();
        return s3Client.getObjectAsBytes(request).asByteArray();
    }

    /**
     * Eliminar un objeto del bucket indicado.
     * @param bucket el bucket lógico donde reside el objeto.
     * @param key la clave del objeto a eliminar.
     */
    public void eliminar(Bucket bucket, String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(nombreBucket(bucket))
                .key(key)
                .build();
        s3Client.deleteObject(request);
    }
}
