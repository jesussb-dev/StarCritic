package com.starcritic.dam_proyect.data.api.rest;

import java.io.File;

/**
 * Acceso al almacenamiento de archivos (Cloudflare R2) a través de la API REST
 * de StarCritic_Server. El cliente no maneja credenciales ni el SDK de R2: el
 * servidor centraliza la subida, la descarga de bytes y el borrado.
 * @author Jesús Santos Baquero
 */
public final class ArchivoApi {

    /** Bucket lógico, mapeado al segmento de ruta del endpoint del servidor. */
    public enum Bucket {
        CERTIFICACIONES("certificaciones"),
        CONTENIDO_LOCAL("contenido-local");

        private final String segmento;

        Bucket(String segmento) {
            this.segmento = segmento;
        }
    }

    private ArchivoApi() {
    }

    /**
     * Subir un archivo al bucket indicado.
     * @param archivo el fichero a subir.
     * @param bucket el bucket destino.
     * @param contentType el tipo MIME del fichero (p.ej. "application/pdf").
     * @return la clave (key) con la que el objeto queda almacenado.
     */
    public static String subir(File archivo, Bucket bucket, String contentType) {
        return ApiClient.get().postMultipart("/archivos/" + bucket.segmento, archivo, contentType);
    }

    /**
     * Eliminar un objeto del bucket indicado.
     * @param bucket el bucket donde reside el objeto.
     * @param key la clave del objeto a eliminar.
     */
    public static void eliminar(Bucket bucket, String key) {
        ApiClient.get().delete("/archivos/" + bucket.segmento + "?key=" + ApiClient.enc(key));
    }

    /**
     * Descargar el contenido completo de un objeto a una carpeta local. El
     * servidor sirve los bytes y el cliente los guarda, reproduciendo el nombre
     * original del fichero (el texto tras el primer '_' de la clave).
     * @param bucket el bucket donde reside el objeto.
     * @param key la clave del objeto.
     * @param carpetaDestino la carpeta donde se guardará el fichero.
     * @return el fichero descargado.
     */
    public static File descargar(Bucket bucket, String key, String carpetaDestino) {
        File carpeta = new File(carpetaDestino);
        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw new RuntimeException("No se pudo crear la carpeta destino: " + carpetaDestino);
        }
        int sep = key.indexOf('_');
        String nombreArchivo = (sep >= 0) ? key.substring(sep + 1) : key;
        File archivoDestino = new File(carpeta, nombreArchivo);

        boolean ok = ApiClient.get().descargarArchivo(
                "/archivos/" + bucket.segmento + "/contenido?key=" + ApiClient.enc(key),
                archivoDestino.toPath());
        if (!ok) {
            throw new RuntimeException("No se pudo descargar el archivo: " + key);
        }
        return archivoDestino;
    }

    /**
     * Descargar un objeto a una carpeta temporal local y devolver la URI del
     * fichero resultante (esquema {@code file:}). Usa el mismo mecanismo que la
     * descarga de PDFs de certificaciones: los bytes los sirve el servidor y el
     * cliente los guarda en local, de modo que el recurso puede cargarse con
     * {@code new URI(...).toURL()} sin necesidad de URLs presignadas.
     * @param bucket el bucket donde reside el objeto.
     * @param key la clave del objeto.
     * @return la URI del fichero descargado, o null si la clave es nula/vacía.
     */
    public static String descargar(Bucket bucket, String key) {
        if (key == null || key.isBlank()) {
            return null;
        }
        String carpetaTemporal = System.getProperty("java.io.tmpdir") + "/StarCritic/posters";
        return descargar(bucket, key, carpetaTemporal).toURI().toString();
    }
}
