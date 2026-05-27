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
     * Búsqueda paginada.
     *
     * @param type {@code "series"} para series; cualquier otro valor (p. ej.
     *             {@code "movie"}) se trata como película.
     */
    public OMDbListSearch search(String query, String type, int page) {
        String recurso = "series".equalsIgnoreCase(type) ? "/omdb/series" : "/omdb/peliculas";
        String path = recurso + "?q=" + ApiClient.enc(query) + "&page=" + page;
        return ApiClient.get().getObject(path, OMDbListSearch.class);
    }

    public OMDbDetailJson detail(String id) {
        return ApiClient.get().getObject("/omdb/detalle/" + ApiClient.enc(id), OMDbDetailJson.class);
    }
}
