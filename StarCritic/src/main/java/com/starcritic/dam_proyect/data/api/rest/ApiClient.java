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

    /**
     * Obtener la instancia compartida del cliente. Se inicializa perezosamente
     * la primera vez que se invoca.
     * @return la instancia compartida del {@link ApiClient}.
     */
    public static ApiClient get() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    // ===================== Verbos HTTP ===================== //

    /**
     * Ejecutar una petición GET y obtener el JSON crudo de la respuesta.
     * @param path la ruta relativa del endpoint a invocar.
     * @return el JSON de la respuesta, o null si el código no es 2xx.
     */
    public JsonElement getJson(String path) {
        HttpResponse<String> resp = enviar(peticion(path).GET().build());
        if (!exito(resp)) {
            return null;
        }
        return JsonParser.parseString(resp.body());
    }

    /**
     * Ejecutar una petición GET y deserializar la respuesta al tipo indicado.
     * @param <T> el tipo destino al que se deserializa la respuesta.
     * @param path la ruta relativa del endpoint a invocar.
     * @param tipo el tipo destino (una {@link Class}, p.ej. {@code X[].class}).
     * @return el objeto deserializado, o null si el código no es 2xx.
     */
    public <T> T getObject(String path, Type tipo) {
        HttpResponse<String> resp = enviar(peticion(path).GET().build());
        if (!exito(resp)) {
            return null;
        }
        return gson.fromJson(resp.body(), tipo);
    }

    /**
     * Ejecutar una petición GET sobre un endpoint que devuelve un booleano crudo.
     * @param path la ruta relativa del endpoint a invocar.
     * @return true si la respuesta es 2xx y su cuerpo es "true", false en caso contrario.
     */
    public boolean getBoolean(String path) {
        HttpResponse<String> resp = enviar(peticion(path).GET().build());
        return exito(resp) && Boolean.parseBoolean(resp.body().trim());
    }

    /**
     * Ejecutar una petición POST con cuerpo JSON y deserializar la respuesta.
     * @param <T> el tipo destino al que se deserializa la respuesta.
     * @param path la ruta relativa del endpoint a invocar.
     * @param cuerpo el objeto a serializar como cuerpo JSON, puede ser null.
     * @param clazz la clase destino para deserializar la respuesta.
     * @return el objeto deserializado, o null si el código no es 2xx.
     */
    public <T> T postObject(String path, Object cuerpo, Class<T> clazz) {
        HttpResponse<String> resp = enviar(peticion(path).POST(publicador(cuerpo)).build());
        if (!exito(resp)) {
            return null;
        }
        return gson.fromJson(resp.body(), clazz);
    }

    /**
     * Ejecutar una petición POST con cuerpo JSON sin importar la respuesta.
     * @param path la ruta relativa del endpoint a invocar.
     * @param cuerpo el objeto a serializar como cuerpo JSON, puede ser null.
     * @return true si la respuesta es 2xx, false en caso contrario.
     */
    public boolean postOk(String path, Object cuerpo) {
        return exito(enviar(peticion(path).POST(publicador(cuerpo)).build()));
    }

    /**
     * Ejecutar una petición PATCH con cuerpo JSON sin importar la respuesta.
     * @param path la ruta relativa del endpoint a invocar.
     * @param cuerpo el objeto a serializar como cuerpo JSON, puede ser null.
     * @return true si la respuesta es 2xx, false en caso contrario.
     */
    public boolean patchOk(String path, Object cuerpo) {
        return exito(enviar(peticion(path).method("PATCH", publicador(cuerpo)).build()));
    }

    /**
     * Ejecutar una petición PUT con cuerpo JSON sin importar la respuesta.
     * @param path la ruta relativa del endpoint a invocar.
     * @param cuerpo el objeto a serializar como cuerpo JSON, puede ser null.
     * @return true si la respuesta es 2xx, false en caso contrario.
     */
    public boolean putOk(String path, Object cuerpo) {
        return exito(enviar(peticion(path).PUT(publicador(cuerpo)).build()));
    }

    /**
     * Ejecutar una petición DELETE sobre el endpoint indicado.
     * @param path la ruta relativa del endpoint a invocar.
     * @return true si la respuesta es 2xx, false en caso contrario.
     */
    public boolean delete(String path) {
        return exito(enviar(peticion(path).DELETE().build()));
    }

    // ===================== Utilidades ===================== //

    /**
     * Codificar un segmento de ruta escapando espacios y caracteres reservados.
     * @param segmento el segmento de URL a codificar.
     * @return el segmento codificado en UTF-8.
     */
    public static String enc(String segmento) {
        return URLEncoder.encode(segmento, StandardCharsets.UTF_8).replace("+", "%20");
    }

    /**
     * Obtener la URL base configurada para la API REST.
     * @return la URL base sin barra final, p.ej. http://172.27.250.2:8080/api
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
