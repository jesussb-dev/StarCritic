package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.AdminContenidoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.api.OMDbListSearch;
import com.starcritic.dam_proyect.model.pojo.api.OMDbSearchJson;
import com.starcritic.dam_proyect.model.pojo.api.RAWGListNormal;
import com.starcritic.dam_proyect.model.pojo.api.RAWGNormalJson;
import com.starcritic.dam_proyect.model.pojo.bd.Contenido;
import com.starcritic.dam_proyect.model.pojo.bd.ContenidoAudiovisual;
import com.starcritic.dam_proyect.model.pojo.bd.Origen;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.model.pojo.bd.SearchResult;
import com.starcritic.dam_proyect.model.pojo.bd.Videojuego;
import com.starcritic.dam_proyect.model.pojo.itemList.ItemContent;
import com.starcritic.dam_proyect.view.CompleteItemDialog;
import com.starcritic.dam_proyect.view.SearchDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SearchController extends BaseController<SearchDialog> {

    private int actualPage = 0;
    private int totalpages = 0;

    public SearchController(SearchDialog view, Model model) {
        super(view, model);
        view.enableDeleteButton(false);
        view.setFilmSelect(true);
        view.enablePassButton(false);
        view.setSearchListListener(searchListListener());
        view.setSearchButtonListener(searchButtonListener());
        view.setAfterButtonListener(afterButtonListener());
        view.setBeforeButtonListener(beforeButtonListener());
        view.setRadioButtonGroupListener(radioButtonGroupListener());
        view.setCancelButtonListener(cancelButtonListener());
    }

    private MouseListener searchListListener() {
        MouseAdapter ms = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goToDetailsItem();
            }
        };
        return ms;
    }

    private ActionListener searchButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualPage = 1;
                view.clearList();
                view.enablePassButton(false);
                addSearchElements(actualPage);
            }
        };
        return al;
    }

    private ActionListener afterButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actualPage >= totalpages) {
                    JOptionPane.showMessageDialog(view, "Error de página", "Error: esta es la última página", JOptionPane.ERROR_MESSAGE);
                } else {
                    actualPage++;
                    view.clearList();
                    view.enablePassButton(false);
                    addSearchElements(actualPage);
                }
            }
        };
        return al;
    }

    private ActionListener beforeButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actualPage <= 1) {
                    JOptionPane.showConfirmDialog(view, "Error de página", "Error: esta es la primera página", JOptionPane.ERROR_MESSAGE);
                } else {
                    actualPage--;
                    view.clearList();
                    view.enablePassButton(false);
                    addSearchElements(actualPage);
                }
            }
        };
        return al;
    }

    private ActionListener radioButtonGroupListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.enablePassButton(false);
                view.clearList();
                view.setTextLabel("");
            }
        };
        return al;
    }

    private ActionListener cancelButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        };
        return al;
    }

    private void goToDetailsItem() {
        int row = view.getSelectedRow();
        if (row == -1) {
            return;
        }
        String id = view.getItemListId(row);
        CompleteItemDialog cid = new CompleteItemDialog(view, true);
        if (view.getItemListOrigen(row).esLocal()) {
            new CompleteItemController(cid, model, Integer.parseInt(id), getTypeItems());
        } else {
            new CompleteItemController(cid, model, id, getTypeItems());
        }
        cid.setVisible(true);
    }

    private void addSearchElements(int page) {
        TipoContenido tipo = getTypeItems();
        String searchText = view.getSearchText();
        BackgroundWork.run(
                () -> {
                    List<ItemContent> result = new ArrayList<>();
                    List<Contenido> catalogo = AdminContenidoDB.obtenerCatalogoCompleto();
                    int pages = 0;
                    if (tipo == TipoContenido.PELICULA) {
                        OMDbListSearch items = model.getOMDb().getFilms(searchText, page);
                        pages = items != null ? items.getTotalResultsAsInt() : 0;
                        buildOMDbItems(items, catalogo, result);
                        buildLocalItems(TipoContenido.PELICULA, catalogo, result);
                    } else if (tipo == TipoContenido.SERIE) {
                        OMDbListSearch items = model.getOMDb().getSeries(searchText, page);
                        pages = items != null ? items.getTotalResultsAsInt() : 0;
                        buildOMDbItems(items, catalogo, result);
                        buildLocalItems(TipoContenido.SERIE, catalogo, result);
                    } else {
                        RAWGListNormal items = model.getRAWG().getGames(searchText, page);
                        pages = items != null ? items.getTotalResultsAsInt() : 0;
                        buildRAWGItems(items, catalogo, result);
                        buildLocalItems(TipoContenido.VIDEOJUEGO, catalogo, result);
                    }
                    return new SearchResult(result, pages);
                },
                result -> {
                    totalpages = result.getTotalPages();
                    result.getItems().forEach(view::addElementSearch);
                    view.setTextLabel("Página: " + actualPage + " de " + totalpages);
                    view.enablePassButton(true);
                },
                err -> {
                    view.enablePassButton(true);
                    JOptionPane.showMessageDialog(view, "Error al realizar la búsqueda", "Error", JOptionPane.ERROR_MESSAGE);
                }
        );
    }

    private void buildOMDbItems(OMDbListSearch items, List<Contenido> catalogo, List<ItemContent> result) {
        if (items == null || items.getContenidos() == null) {
            return;
        }
        for (OMDbSearchJson item : items.getContenidos()) {
            ImageIcon image = loadRemoteImage(item.getPoster());
            Contenido registrado = buscarRegistradoOMDb(catalogo, item.getImdbId());
            if (registrado != null) {
                if (!registrado.isOculto()) {
                    result.add(new ItemContent(buildLabel(registrado.getTitulo(), registrado.isDestacado()),
                            image, String.valueOf(registrado.getIdContenido()), Origen.LOCAL));
                }
            } else {
                result.add(new ItemContent(buildLabel(item.getTitle(), false),
                        image, item.getImdbId(), Origen.OMDB));
            }
        }
    }

    private void buildRAWGItems(RAWGListNormal items, List<Contenido> catalogo, List<ItemContent> result) {
        if (items == null || items.getJuegos() == null) {
            return;
        }
        for (RAWGNormalJson game : items.getJuegos()) {
            ImageIcon image = loadRemoteImage(game.getBackgroundImage());
            Contenido registrado = buscarRegistradoRAWG(catalogo, game.getId());
            if (registrado != null) {
                if (!registrado.isOculto()) {
                    result.add(new ItemContent(buildLabel(registrado.getTitulo(), registrado.isDestacado()),
                            image, String.valueOf(registrado.getIdContenido()), Origen.LOCAL));
                }
            } else {
                result.add(new ItemContent(buildLabel(game.getName(), false),
                        image, String.valueOf(game.getId()), Origen.RAWG));
            }
        }
    }

    private void buildLocalItems(TipoContenido tipo, List<Contenido> catalogo, List<ItemContent> result) {
        ImageIcon defaultImage = new ImageIcon(getClass().getResource("/img/defaultPoster.png"));
        for (Contenido content : catalogo) {
            if (content.getOrigen() == Origen.LOCAL && content.getTipoContenido() == tipo && !content.isOculto()) {
                String label = buildLabel(content.getTitulo(), content.isDestacado());
                result.add(new ItemContent(label, defaultImage, String.valueOf(content.getIdContenido()), content.getOrigen()));
            }
        }
    }

    private Contenido buscarRegistradoOMDb(List<Contenido> catalogo, String imdbId) {
        if (imdbId == null) {
            return null;
        }
        for (Contenido c : catalogo) {
            if (c instanceof ContenidoAudiovisual && imdbId.equals(((ContenidoAudiovisual) c).getIdOmdb())) {
                return c;
            }
        }
        return null;
    }

    private Contenido buscarRegistradoRAWG(List<Contenido> catalogo, int idRawg) {
        for (Contenido c : catalogo) {
            if (c instanceof Videojuego && ((Videojuego) c).getIdRawg() == idRawg) {
                return c;
            }
        }
        return null;
    }

    private String buildLabel(String titulo, boolean destacado) {
        return titulo + (destacado ? "  ★" : "");
    }

    private ImageIcon loadRemoteImage(String url) {
        if (url != null) {
            try {
                return new ImageIcon(new URI(url).toURL());
            } catch (URISyntaxException | MalformedURLException ex) {
                return new ImageIcon(getClass().getResource("/img/defaultPoster.png"));
            }
        }
        return new ImageIcon(getClass().getResource("/img/defaultPoster.png"));
    }

    private TipoContenido getTypeItems() {
        if (view.isFilmSelected()) {
            return TipoContenido.PELICULA;
        }
        if (view.isSeriesSelected()) {
            return TipoContenido.SERIE;
        }
        if (view.isVideogamesSelected()) {
            return TipoContenido.VIDEOJUEGO;
        }
        return null;
    }

}
