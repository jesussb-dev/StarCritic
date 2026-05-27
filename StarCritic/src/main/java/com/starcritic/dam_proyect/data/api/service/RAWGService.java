package com.starcritic.dam_proyect.data.api.service;

import com.starcritic.dam_proyect.data.api.repository.RAWGRepository;
import com.starcritic.dam_proyect.model.pojo.api.RAWGListNormal;
import com.starcritic.dam_proyect.model.pojo.api.RAWGNormalJson;

/**
 * Servicio de videojuegos. Mantiene la misma API pública que la versión de
 * escritorio, pero los datos llegan del backend (StarCritic_Server) vía
 * {@link RAWGRepository}.
 *
 * @author Jesús Santos Baquero
 */
public class RAWGService {

    private RAWGRepository repoRAWG;

    public RAWGService() {
        this.repoRAWG = new RAWGRepository();
    }

    /**
     * Buscar videojuegos en RAWG. Requiere al menos 3 caracteres para evitar
     * búsquedas demasiado amplias.
     * @param game el nombre a buscar.
     * @param page el número de página.
     * @return la respuesta paginada, o null si el texto es demasiado corto.
     */
    public RAWGListNormal getGames(String game, int page) {
        if (game.length() >= 3) {
            return repoRAWG.search(game, page);
        } else {
            return null;
        }
    }

    /**
     * Obtener la ficha de detalle de un videojuego de RAWG.
     * @param id el identificador del videojuego en RAWG.
     * @return los detalles del videojuego, o null si la petición falla.
     */
    public RAWGNormalJson getGame(String id) {
        return repoRAWG.getGameDetails(id);
    }

    public RAWGRepository getRepoRAWG() {
        return repoRAWG;
    }

    public void setRepoRAWG(RAWGRepository repoRAWG) {
        this.repoRAWG = repoRAWG;
    }
}
