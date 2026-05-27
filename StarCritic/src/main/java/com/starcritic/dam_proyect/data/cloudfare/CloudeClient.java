/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.data.cloudfare;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

/**
 * Cliente para Cloudflare R2 vía la API compatible con S3. Permite subir,
 * descargar, generar URLs presignadas y eliminar objetos en los buckets de
 * certificaciones y contenido local.
 * @author Jesús Santos Baquero
 */
public class CloudeClient {

    public enum Cubo { CERTIFICACIONES, CONTENIDO_LOCAL }

    private static String CONFIG_PATH = "config.properties";
    private final String bucketName;
    private S3Client buildS3Client;
    private S3Presigner presigner;

    public CloudeClient() {
        this(Cubo.CERTIFICACIONES);
    }

    public CloudeClient(Cubo cubo) {
        String bucketKey = switch (cubo) {
            case CERTIFICACIONES -> "BUCKET_CLOUDFARE";
            case CONTENIDO_LOCAL -> "BUCKET_USER_IMAGE";
        };
        this.bucketName = getApiKey(bucketKey);
        this.buildS3Client = buildS3Client();
        this.presigner = buildS3Presigner();
    }

    private String getApiKey(String key) {
        String cachedApiKey = "";
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_PATH)) {
            if (input == null) {
                throw new RuntimeException("No se encontró config.properties");
            }
            props.load(input);
            cachedApiKey = props.getProperty(key, "").trim();
        } catch (IOException ex) {
            System.out.println("Error al leer config.properties");
        }

        if (cachedApiKey.isEmpty()) {
            System.out.println("API key no configurada para: " + key);
        }

        return cachedApiKey;
    }

    private AwsBasicCredentials getCredentials() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                getApiKey("ACCESS_KEY_CLOUDFARE"),
                getApiKey("SECRECT_ACCES_KEY_CLOUDFARE")
        );
        return credentials;
    }

    private S3Client buildS3Client() {
        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .chunkedEncodingEnabled(false)
                .build();

        return S3Client.builder()
                .endpointOverride(URI.create(getApiKey("ENDPOINT_CLOUDFARE")))
                .credentialsProvider(StaticCredentialsProvider.create(getCredentials()))
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    private S3Presigner buildS3Presigner() {
        return S3Presigner.builder()
                .endpointOverride(URI.create(getApiKey("ENDPOINT_CLOUDFARE")))
                .credentialsProvider(StaticCredentialsProvider.create(getCredentials()))
                .region(Region.of("auto"))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }

    /**
     * Subir un archivo al bucket configurado. El nombre del objeto se prefija
     * con un UUID aleatorio para evitar colisiones.
     * @param archivo el fichero a subir.
     * @param contentType el tipo MIME del fichero (p.ej. "image/png").
     * @return la clave (key) con la que el objeto queda almacenado en el bucket.
     */
    public String subirArchivo(File archivo, String contentType) {
        String key = UUID.randomUUID() + "_" + archivo.getName();
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        buildS3Client.putObject(request, RequestBody.fromFile(archivo));
        return key;
    }

    /**
     * Descargar un objeto del bucket a una carpeta local.
     * @param key la clave del objeto en el bucket.
     * @param carpetaDestino la carpeta destino donde se guardará el fichero.
     * @return el fichero descargado.
     */
    public File descargarArchivo(String key, String carpetaDestino) {
        File carpeta = new File(carpetaDestino);
        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw new RuntimeException("No se pudo crear la carpeta destino: " + carpetaDestino);
        }

        int sep = key.indexOf('_');
        String nombreArchivo = (sep >= 0) ? key.substring(sep + 1) : key;
        File archivoDestino = new File(carpeta, nombreArchivo);

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            // getObject(Path) falla si el fichero ya existe; borra el remanente
            // de una descarga anterior para poder sobrescribirlo.
            Files.deleteIfExists(archivoDestino.toPath());
            buildS3Client.getObject(request, archivoDestino.toPath());
            return archivoDestino;
        } catch (NoSuchKeyException e) {
            throw new RuntimeException("El objeto no existe en el bucket: " + key, e);
        } catch (S3Exception e) {
            throw new RuntimeException("Error al descargar de R2: " + e.awsErrorDetails().errorMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo preparar el fichero destino: " + archivoDestino, e);
        }
    }

    /**
     * Generar una URL temporal (presignada) de lectura para un objeto del bucket.
     * @param key la clave del objeto en el bucket.
     * @param minutos los minutos de validez de la URL.
     * @return la URL presignada como cadena.
     */
    public String urlPresignada(String key, int minutos) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        GetObjectPresignRequest presign = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(minutos))
                .getObjectRequest(request)
                .build();
        return presigner.presignGetObject(presign).url().toString();
    }

    /**
     * Eliminar un objeto del bucket.
     * @param key la clave del objeto a eliminar.
     */
    public void eliminarArchivo(String key) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            buildS3Client.deleteObject(request);

        } catch (S3Exception e) {
            throw new RuntimeException("Error al eliminar el archivo: "
                    + e.awsErrorDetails().errorMessage(), e);
        }
    }
}
