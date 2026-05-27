package com.starcritic.dam_proyect.data.api.repository;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.api.RAWGListNormal;
import com.starcritic.dam_proyect.model.pojo.api.RAWGNormalJson;

/**
 * Repositorio para videojuegos, obtenidos a través del backend
 * (StarCritic_Server), que a su vez consulta la API de RAWG.
 *
 * @author Jesús Santos Baquero
 */
public class RAWGRepository {

    public RAWGListNormal search(String query, int page) {
        String path = "/rawg/juegos?q=" + ApiClient.enc(query) + "&page=" + page;
        return ApiClient.get().getObject(path, RAWGListNormal.class);
    }

    public RAWGNormalJson getGameDetails(String id) {
        return ApiClient.get().getObject("/rawg/detalle/" + ApiClient.enc(id), RAWGNormalJson.class);
    }
}
