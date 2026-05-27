package com.starcritic.dam_proyect.data.api.service;

import com.starcritic.dam_proyect.data.api.repository.OMDbRepository;
import com.starcritic.dam_proyect.model.pojo.api.OMDbDetailJson;
import com.starcritic.dam_proyect.model.pojo.api.OMDbListSearch;

/**
 * Servicio de películas y series. Mantiene la misma API pública que la versión
 * de escritorio, pero los datos llegan del backend (StarCritic_Server) vía
 * {@link OMDbRepository}.
 *
 * @author Jesús Santos Baquero
 */
public class OMDbService {

    private OMDbRepository repoOMDb;

    public OMDbService() {
        this.repoOMDb = new OMDbRepository();
    }

    public OMDbListSearch getFilms(String film, int page) {
        if (film.length() >= 3) {
            return repoOMDb.search(film, "movie", page);
        } else {
            return null;
        }
    }

    public OMDbListSearch getSeries(String serie, int page) {
        if (serie.length() >= 3) {
            return repoOMDb.search(serie, "series", page);
        } else {
            return null;
        }
    }

    public OMDbDetailJson getDetails(String id) {
        return repoOMDb.detail(id);
    }

    public OMDbRepository getRepoOMDb() {
        return repoOMDb;
    }

    public void setRepoOMDb(OMDbRepository repoOMDb) {
        this.repoOMDb = repoOMDb;
    }
}
