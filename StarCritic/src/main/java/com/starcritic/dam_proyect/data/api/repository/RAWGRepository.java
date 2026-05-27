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

    /**
     * Búsqueda paginada de videojuegos por nombre.
     * @param query el texto a buscar.
     * @param page el número de página (los resultados se paginan).
     * @return la respuesta de RAWG envuelta en {@link RAWGListNormal}, o null si la petición falla.
     */
    public RAWGListNormal search(String query, int page) {
        String path = "/rawg/juegos?q=" + ApiClient.enc(query) + "&page=" + page;
        return ApiClient.get().getObject(path, RAWGListNormal.class);
    }

    /**
     * Obtener la ficha de detalle de un videojuego por su identificador RAWG.
     * @param id el identificador del videojuego en RAWG.
     * @return los detalles del videojuego, o null si la petición falla.
     */
    public RAWGNormalJson getGameDetails(String id) {
        return ApiClient.get().getObject("/rawg/detalle/" + ApiClient.enc(id), RAWGNormalJson.class);
    }
}
