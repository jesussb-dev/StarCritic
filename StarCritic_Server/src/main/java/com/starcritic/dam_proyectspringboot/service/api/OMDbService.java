package com.starcritic.dam_proyectspringboot.service.api;

import com.starcritic.dam_proyectspringboot.model.api.omdb.OMDbDetailJson;
import com.starcritic.dam_proyectspringboot.model.api.omdb.OMDbListSearch;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @author Jesús Santos Baquero
 */
@Service
public class OMDbService {
    /**
     * Decisión de diseño ya que la API puede devolver "Too many results"
     * esto es mas frecuente en pocos caracteres aunque no siempre.
     */
    private static final int MIN_QUERY_LENGTH = 3;

    @Value("${omdb.api.key}")
    private String apiKey;

    @Value("${omdb.api.url}")
    private String baseUrl;

    private final RestClient restClient;

    public OMDbService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }
    /**
     * Metodo para obtener las peliculas, esta API da los resultados por
     * páginas, por lo tanto adapateremos el metodo a eso.
     * @param query lo que se usara para hacer la busqueda
     * @param page la página que se buscara
     * @return la página con las peliculas en formato "lista"
     */
    public OMDbListSearch getPeliculas(String query, int page) {
        if (query == null || query.length() < MIN_QUERY_LENGTH) return null;
        return search(query, "movie", page);
    }
    /**
    * Metodo para obtener las series, esta API da los resultados por
     * páginas, por lo tanto adapateremos el metodo a eso.
     * @param query lo que se usara para hacer la busqueda
     * @param page la página que se buscara
     * @return la página con las series en formato "lista"
     */
    public OMDbListSearch getSeries(String query, int page) {
        if (query == null || query.length() < MIN_QUERY_LENGTH) return null;
        return search(query, "series", page);
    }
    /**
     * Hace una llamada a la API a traves del cliente configurado y
     * devuelve la respuesta (retrieve()) en el formato solicitado
     * @param imdbId es id propio de la API OMDb para hacer la busqueda
     * @return el json con los datos del objeto en el formato solicitado
     */
    public OMDbDetailJson getDetalle(String imdbId) {
        URI uri = URI.create(baseUrl + "?apikey=" + apiKey
                + "&i=" + URLEncoder.encode(imdbId, StandardCharsets.UTF_8)
                + "&plot=full");
        return restClient.get().uri(uri).retrieve().body(OMDbDetailJson.class);
    }
    /**
     * Hace una llamada a la API a traves del cliente configurado y
     * devuelve la respuesta (retrieve()) en el formato solicitado
     * @param query lo que se usara para hacer la busqueda
     * @param type tipo de contenido a buscar (serie o pelicula)
     * @param page la página que se buscara
     * @return la página seleccionada en el formato solicitado
     */
    private OMDbListSearch search(String query, String type, int page) {
        URI uri = URI.create(baseUrl + "?apikey=" + apiKey
                + "&s=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                + "&type=" + type
                + "&page=" + page);
        return restClient.get().uri(uri).retrieve().body(OMDbListSearch.class);
    }
}
