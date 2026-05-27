package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.cloudfare.CloudeClient;
import com.starcritic.dam_proyect.data.database.RecomendacionDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.api.OMDbDetailJson;
import com.starcritic.dam_proyect.model.pojo.api.RAWGNormalJson;
import com.starcritic.dam_proyect.model.pojo.bd.Origen;
import com.starcritic.dam_proyect.model.pojo.bd.RecommendedItem;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.model.pojo.itemList.ItemContent;
import com.starcritic.dam_proyect.view.CompleteItemDialog;
import com.starcritic.dam_proyect.view.MainNavigationFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 * Controlador encargado de cargar las recomendaciones de cada tipo de contenido
 * (películas, series y videojuegos) en la pantalla principal.
 * @author Jesús Santos Baquero
 */
public class RecommendationController extends BaseController<MainNavigationFrame> {

    private CloudeClient posterCloud;

    public RecommendationController(MainNavigationFrame view, Model model) {
        super(view, model);
        bindListeners();
    }

    private CloudeClient posterCloud() {
        if (posterCloud == null) {
            posterCloud = new CloudeClient(CloudeClient.Cubo.CONTENIDO_LOCAL);
        }
        return posterCloud;
    }

    /**
     * Cargar las recomendaciones de películas, series y videojuegos para un
     * usuario en la vista principal.
     * @param idUsuario el identificador del usuario, o -1 para recomendaciones globales.
     */
    public void load(int idUsuario) {
        view.clearFilms();
        view.clearSeries();
        view.clearVideogames();
        loadType(TipoContenido.PELICULA,   idUsuario);
        loadType(TipoContenido.SERIE,      idUsuario);
        loadType(TipoContenido.VIDEOJUEGO, idUsuario);
    }

    private void loadType(TipoContenido tipo, int idUsuario) {
        BackgroundWork.run(
            () -> {
                List<RecommendedItem> items =
                        new RecomendacionDB().obtenerContenidosRecomendados(tipo,null).getContenidos();
                List<ItemContent> result = new ArrayList<>();
                for (RecommendedItem ri : items) {
                    String posterUrl = resolvePosterUrl(ri);
                    ImageIcon icon = loadImage(posterUrl);
                    result.add(new ItemContent(ri.getTitulo(), icon, ri.getEffectiveId(), ri.getOrigen()));
                }
                return result;
            },
            items -> {
                for (ItemContent ic : items) {
                    switch (tipo) {
                        case PELICULA   -> view.addElementFilm(ic);
                        case SERIE      -> view.addElementSerie(ic);
                        case VIDEOJUEGO -> view.addElementVideogame(ic);
                    }
                }
            },
            err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista","Error", JOptionPane.ERROR_MESSAGE));
    }

    private void bindListeners() {
        view.setFilmListListener(filmListListener());
        view.setSeriesListListener(seriesListListener());
        view.setVideogamesListListener(videogamesListListener());
    }

    private MouseListener filmListListener() {
        return new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                openDetail(view.getFilmList(), TipoContenido.PELICULA);
            }
        };
    }

    private MouseListener seriesListListener() {
        return new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                openDetail(view.getSeriesList(), TipoContenido.SERIE);
            }
        };
    }

    private MouseListener videogamesListListener() {
        return new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                openDetail(view.getVideogamesList(), TipoContenido.VIDEOJUEGO);
            }
        };
    }

    private void openDetail(JList<ItemContent> list, TipoContenido tipo) {
        int index = list.getSelectedIndex();
        if (index < 0) return;
        ItemContent item = list.getModel().getElementAt(index);
        CompleteItemDialog cid = new CompleteItemDialog(view, true);
        if (item.getOrigen() == Origen.LOCAL) {
            new CompleteItemController(cid, model, Integer.parseInt(item.getId()), tipo);
        } else {
            new CompleteItemController(cid, model, item.getId(), tipo);
        }
        cid.setVisible(true);
    }

    private String resolvePosterUrl(RecommendedItem ri) {
        if (ri.getOrigen() == Origen.LOCAL) {
            String key = ri.getPosterKey();
            if (key == null || key.isBlank()) {
                return null;
            }
            try {
                return posterCloud().urlPresignada(key, 60);
            } catch (Exception ex) {
                return null;
            }
        }
        String apiId = ri.getApiId();
        if (apiId == null || apiId.isBlank()) return null;
        if (ri.getOrigen() == Origen.OMDB) {
            OMDbDetailJson detail = model.getOMDb().getDetails(apiId.trim());
            return (detail != null) ? detail.getPoster() : null;
        }
        if (ri.getOrigen() == Origen.RAWG) {
            RAWGNormalJson game = model.getRAWG().getGame(apiId.trim());
            return (game != null) ? game.getBackgroundImage() : null;
        }
        return null;
    }

    private ImageIcon loadImage(String url) {
        if (url != null && !url.isBlank()) {
            try {
                return new ImageIcon(new URI(url).toURL());
            } catch (Exception ignored) {}
        }
        return new ImageIcon(getClass().getResource("/img/defaultPoster.png"));
    }
}
