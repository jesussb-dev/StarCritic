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

    public RAWGListNormal getGames(String game, int page) {
        if (game.length() >= 3) {
            return repoRAWG.search(game, page);
        } else {
            return null;
        }
    }

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
