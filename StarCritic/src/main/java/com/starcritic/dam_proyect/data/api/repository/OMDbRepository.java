package com.starcritic.dam_proyect.data.api.repository;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.api.OMDbDetailJson;
import com.starcritic.dam_proyect.model.pojo.api.OMDbListSearch;

/**
 * Repositorio para películas y series, obtenidas a través del backend
 * (StarCritic_Server), que a su vez consulta la API de OMDb.
 *
 * @author Jesús Santos Baquero
 */
public class OMDbRepository {

    /**
     * Búsqueda paginada de películas o series por título.
     * @param query el texto a buscar.
     * @param type el tipo de contenido: "series" para series, cualquier otro valor (p.ej. "movie") como película.
     * @param page el número de página (los resultados se paginan de 10 en 10).
     * @return la respuesta de OMDb envuelta en {@link OMDbListSearch}, o null si la petición falla.
     */
    public OMDbListSearch search(String query, String type, int page) {
        String recurso = "series".equalsIgnoreCase(type) ? "/omdb/series" : "/omdb/peliculas";
        String path = recurso + "?q=" + ApiClient.enc(query) + "&page=" + page;
        return ApiClient.get().getObject(path, OMDbListSearch.class);
    }

    /**
     * Obtener la ficha de detalle de una película o serie por su identificador OMDb.
     * @param id el identificador IMDb (p.ej. tt0133093).
     * @return los detalles del contenido, o null si la petición falla.
     */
    public OMDbDetailJson detail(String id) {
        return ApiClient.get().getObject("/omdb/detalle/" + ApiClient.enc(id), OMDbDetailJson.class);
    }
}
