package com.starcritic.dam_proyect.data.database;

import com.starcritic.dam_proyect.data.api.rest.ApiClient;
import com.starcritic.dam_proyect.model.pojo.bd.RecommendedItem;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.model.pojo.bd.listas.DetallesRecommendedItem;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Recomendaciones obtenidas vía API REST.
 *
 * @author Jesús Santos Baquero
 */
public class RecomendacionDB {

    public DetallesRecommendedItem obtenerContenidosRecomendados(TipoContenido type, String idUsuario) {
        StringBuilder path = new StringBuilder("/recomendaciones?tipo=").append(type.toString());
        if (idUsuario != null) {
            path.append("&idUsuario=").append(idUsuario);
        }
        RecommendedItem[] respuesta = ApiClient.get().getObject(path.toString(), RecommendedItem[].class);
        DetallesRecommendedItem detalles = new DetallesRecommendedItem();
        if (respuesta != null) {
            detalles.setContenidos(new ArrayList<>(Arrays.asList(respuesta)));
        } else {
            detalles.setContenidos(new ArrayList<>());
        }
        return detalles;
    }
}
