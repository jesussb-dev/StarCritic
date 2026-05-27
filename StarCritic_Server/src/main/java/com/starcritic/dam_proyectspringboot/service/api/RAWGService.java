package com.starcritic.dam_proyectspringboot.service.api;

import com.starcritic.dam_proyectspringboot.model.api.rawg.RAWGListNormal;
import com.starcritic.dam_proyectspringboot.model.api.rawg.RAWGNormalJson;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RAWGService {
    /**
     * Decisión de diseño ya que la API no devuelve "Too many results",
     * pero al ser tantos objetos tarda demasiado (más de 10 segundos)
     * esto es mas frecuente en pocos caracteres aunque no siempre.
     */
    private static final int MIN_QUERY_LENGTH = 3;

    @Value("${rawg.api.key}")
    private String apiKey;

    @Value("${rawg.api.url}")
    private String baseUrl;

    private final RestClient restClient;

    public RAWGService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }
    /**
     * Metodo para obtener los videojuegos, esta API da los resultados por
     * páginas, por lo tanto adapateremos el metodo a eso.
     * @param query lo que se usara para hacer la busqueda
     * @param page la página que se buscara
     * @return la página con los videojuegos en formato "lista"
     */    
    public RAWGListNormal getJuegos(String query, int page) {
        if (query == null || query.length() < MIN_QUERY_LENGTH) return null;
        URI uri = URI.create(baseUrl + "?key=" + apiKey
                + "&search=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                + "&page=" + page);
        return restClient.get().uri(uri).retrieve().body(RAWGListNormal.class);
    }
    /**
     * Hace una llamada a la API a traves del cliente configurado y
     * devuelve la respuesta (retrieve()) en el formato solicitado
     * @param id es id propio de la API RAWG  para hacer la busqueda
     * @return el json con los datos del objeto en el formato solicitado
     */
    public RAWGNormalJson getDetalle(int id) {
        URI uri = URI.create(baseUrl + "/" + id + "?key=" + apiKey);
        return restClient.get().uri(uri).retrieve().body(RAWGNormalJson.class);
    }
}
