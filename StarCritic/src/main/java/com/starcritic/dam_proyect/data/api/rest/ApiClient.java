package com.starcritic.dam_proyect.data.api.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Cliente HTTP único frente a la API REST del backend (StarCritic_Server).
 * Centraliza la URL base, un {@link Gson} con adaptadores de fecha y los verbos
 * HTTP más usados envueltos en métodos de conveniencia
 * ({@link #getObject}, {@link #postObject}, {@link #delete}…).
 *
 * <p>Las clases {@code *DB} obtienen la instancia compartida con {@link #get()}.
 * Convención de errores: los fallos de red se propagan como {@link ApiException}
 * (sin comprobar) hasta el callback de error de {@code BackgroundWork}; las
 * respuestas con código distinto de 2xx devuelven {@code null} (objetos) o
 * {@code false} (operaciones), sin lanzar.</p>
 *
 * @author Jesús Santos Baquero
 */
public class ApiClient {

    private static final String CONFIG_PATH = "config.properties";
    protected static final String BASE_URL = getProperty("API_BASE_URL");
    private static Properties cachedProps;
    private static ApiClient instance;

    protected Gson gson;
    protected HttpClient client;

    public ApiClient() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        this.client = HttpClient.newHttpClient();
    }

    /** Instancia compartida (perezosa) que usan las clases {@code *DB}. */
    public static ApiClient get() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    // ===================== Verbos HTTP ===================== //

    /** GET que devuelve el JSON crudo, o {@code null} si la respuesta no es 2xx. */
    public JsonElement getJson(String path) {
        HttpResponse<String> resp = enviar(peticion(path).GET().build());
        if (!exito(resp)) {
            return null;
        }
        return JsonParser.parseString(resp.body());
    }

    /** GET deserializado al tipo indicado (una {@link Class}, p. ej. {@code X[].class} o {@code LinkedHashMap.class}). */
    public <T> T getObject(String path, Type tipo) {
        HttpResponse<String> resp = enviar(peticion(path).GET().build());
        if (!exito(resp)) {
            return null;
        }
        return gson.fromJson(resp.body(), tipo);
    }

    /** GET de un endpoint que responde un booleano crudo ({@code true}/{@code false}). */
    public boolean getBoolean(String path) {
        HttpResponse<String> resp = enviar(peticion(path).GET().build());
        return exito(resp) && Boolean.parseBoolean(resp.body().trim());
    }

    /** POST con cuerpo JSON; devuelve la respuesta deserializada o {@code null}. */
    public <T> T postObject(String path, Object cuerpo, Class<T> clazz) {
        HttpResponse<String> resp = enviar(peticion(path).POST(publicador(cuerpo)).build());
        if (!exito(resp)) {
            return null;
        }
        return gson.fromJson(resp.body(), clazz);
    }

    /** POST cuyo único interés es si tuvo éxito (2xx). */
    public boolean postOk(String path, Object cuerpo) {
        return exito(enviar(peticion(path).POST(publicador(cuerpo)).build()));
    }

    /** PATCH que devuelve {@code true} si la respuesta es 2xx. */
    public boolean patchOk(String path, Object cuerpo) {
        return exito(enviar(peticion(path).method("PATCH", publicador(cuerpo)).build()));
    }

    /** PUT que devuelve {@code true} si la respuesta es 2xx. */
    public boolean putOk(String path, Object cuerpo) {
        return exito(enviar(peticion(path).PUT(publicador(cuerpo)).build()));
    }

    /** DELETE que devuelve {@code true} si la respuesta es 2xx. */
    public boolean delete(String path) {
        return exito(enviar(peticion(path).DELETE().build()));
    }

    // ===================== Utilidades ===================== //

    /** Codifica un segmento de ruta (espacios y caracteres reservados). */
    public static String enc(String segmento) {
        return URLEncoder.encode(segmento, StandardCharsets.UTF_8).replace("+", "%20");
    }

    /**
     * @return la URL base de la API (sin barra final),
     *         p.ej. {@code http://172.27.250.2:8080/api}
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }

    // ===================== Internos ===================== //

    private static HttpRequest.Builder peticion(String path) {
        return HttpRequest.newBuilder(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    private HttpRequest.BodyPublisher publicador(Object cuerpo) {
        if (cuerpo == null) {
            return HttpRequest.BodyPublishers.noBody();
        }
        String json;
        if (cuerpo instanceof JsonElement) {
            json = cuerpo.toString();
        } else {
            json = gson.toJson(cuerpo);
        }
        return HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8);
    }

    private HttpResponse<String> enviar(HttpRequest req) {
        try {
            return client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ex) {
            throw new ApiException("Error de comunicación con la API: " + req.method() + " " + req.uri(), ex);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new ApiException("Petición interrumpida: " + req.method() + " " + req.uri(), ex);
        }
    }

    private static boolean exito(HttpResponse<?> resp) {
        return resp != null && resp.statusCode() >= 200 && resp.statusCode() < 300;
    }

    // ===================== Configuración ===================== //

    protected static String getProperty(String key) {
        if (cachedProps == null) {
            cachedProps = new Properties();
            try (InputStream input = ApiClient.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
                if (input == null) {
                    System.err.println("No se encontró config.properties en el classpath");
                    return "";
                }
                cachedProps.load(input);
            } catch (IOException ex) {
                System.err.println("Error al leer config.properties");
                ex.printStackTrace();
                return "";
            }
        }
        String value = cachedProps.getProperty(key, "").trim();
        if (value.isEmpty()) {
            System.err.println("Propiedad no configurada: " + key);
        }
        return value;
    }

    // ===================== Adaptadores de fecha (ISO-8601) ===================== //

    private static final class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate src, Type t, JsonSerializationContext c) {
            return new JsonPrimitive(src.toString());
        }
        @Override
        public LocalDate deserialize(JsonElement json, Type t, JsonDeserializationContext c) {
            return LocalDate.parse(json.getAsString());
        }
    }

    private static final class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime src, Type t, JsonSerializationContext c) {
            return new JsonPrimitive(src.toString());
        }
        @Override
        public LocalDateTime deserialize(JsonElement json, Type t, JsonDeserializationContext c) {
            return LocalDateTime.parse(json.getAsString());
        }
    }
}
