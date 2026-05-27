package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.ContenidoDB;
import com.starcritic.dam_proyect.data.database.ListaContenidoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.api.OMDbDetailJson;
import com.starcritic.dam_proyect.model.pojo.api.RAWGNormalJson;
import com.starcritic.dam_proyect.model.pojo.bd.Contenido;
import com.starcritic.dam_proyect.model.pojo.bd.Origen;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
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

/**
 * Controlador de la vista de búsqueda dentro de una lista personal. Permite
 * filtrar los contenidos de una lista concreta y abrir su detalle.
 * @author Jesús Santos Baquero
 */
public class SearchListController extends BaseController<SearchDialog> {

    private  String nombreLista;
    private final List<ListRow> rows = new ArrayList<>();

    /** Fila mostrada en la lista, alineada 1:1 con las filas de la {@link SearchDialog}. */
    private static final class ListRow {
        final int idDB;
        final TipoContenido tipo;
        final String apiId; // id externo (OMDb/RAWG); null si el contenido es local
        final ItemContent item;

        ListRow(int idDB, TipoContenido tipo, String apiId, ItemContent item) {
            this.idDB = idDB;
            this.tipo = tipo;
            this.apiId = apiId;
            this.item = item;
        }
    }

    public SearchListController(SearchDialog view, Model model, String nombreLista) {
        super(view, model);
        this.nombreLista = nombreLista;
        initComponents();
        view.setSearchListListener(searchListListener());
        view.setSearchButtonListener(searchButtonListener());
        view.setDeleteButtonListener(deleteButtonListener());
        view.setCancelButtonListener(cancelButtonListener());
    }

    private MouseListener searchListListener() {
        return new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { goToDetailsItem(); }
            @Override public void mouseReleased(MouseEvent e) { goToDetailsItem(); }
        };
    }

    private ActionListener searchButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarContenidosLista();
            }
        };
        return al;
    }

    private ActionListener deleteButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        int row = view.getSelectedRow();
        if (row < 0 || row >= rows.size()) return;
        int confirm = JOptionPane.showConfirmDialog(view,"¿Estás seguro de eliminar este elemento de la lista?","Eliminar elemento", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        int idDB = rows.get(row).idDB;
        BackgroundWork.run(
            () -> {
                ListaContenidoDB.eliminarContenidoDeLista(model.getUser().getIdUsuario(), nombreLista, idDB);
                return null;
            },
            v -> {
                JOptionPane.showMessageDialog(view,"Se ha borrado el contenido de la lista correctamente.","Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                view.deleteListElement(row);
                rows.remove(row);
            },
            err -> JOptionPane.showMessageDialog(view, "Error al eliminar el contenido", "Error", JOptionPane.ERROR_MESSAGE)
        );
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

    private void initComponents() {
        view.enablePassButton(false);
        view.setTextLabel("");
        view.enableRadioButtons(false);
        cargarContenidosLista();
    }

    private void cargarContenidosLista() {
        String filtro = view.getSearchText();
        view.clearList();
        rows.clear();
        BackgroundWork.run(
            () -> buildRows(filtro),
            newRows -> {
                for (ListRow r : newRows) {
                    rows.add(r);
                    view.addElementSearch(r.item);
                }
            },err -> JOptionPane.showMessageDialog(view, "Error al cargar el contenido de la lista","Error", JOptionPane.ERROR_MESSAGE));

    }

    private List<ListRow> buildRows(String filtro) {
        String safeFilter = filtro != null ? filtro.toLowerCase() : "";
        List<ListRow> result = new ArrayList<>();
        List<Integer> ids = new ArrayList<>(ListaContenidoDB.obtenerContenidoPorLista(model.getUser().getIdUsuario(), nombreLista).keySet());
        for (int idDB : ids) {
            TipoContenido tipo = ContenidoDB.obtenerTipoContenido(idDB);
            if (tipo == null) continue;
            String apiId = ContenidoDB.obtenerApiId(idDB, tipo);
            ListRow row = (apiId != null)
                    ? buildApiRow(idDB, tipo, apiId, safeFilter)
                    : buildLocalRow(idDB, safeFilter);
            if (row != null) {
                result.add(row);
            }
        }
        return result;
    }

    private ListRow buildApiRow(int idDB, TipoContenido tipo, String apiId, String filtro) {
        String titulo = null;
        String poster = null;
        if (tipo.esAudiovisual()) {
            OMDbDetailJson item = model.getOMDb().getDetails(apiId);
            if (item != null) {
                titulo = item.getTitle();
                poster = item.getPoster();
            }
        } else {
            RAWGNormalJson game = model.getRAWG().getGame(apiId);
            if (game != null) {
                titulo = game.getName();
                poster = game.getBackgroundImage();
            }
        }
        // Si la API externa falla, recurrimos a los datos locales para que el
        // elemento guardado nunca desaparezca de la lista.
        if (titulo == null) {
            Contenido contenido = ContenidoDB.obtenerContenido(idDB);
            if (contenido != null) {
                titulo = contenido.getTitulo();
                poster = contenido.getPosterKey();
            }
        }
        if (titulo == null || !titulo.toLowerCase().contains(filtro)) {
            return null;
        }
        return new ListRow(idDB, tipo, apiId, new ItemContent(titulo, loadIcon(poster), apiId));
    }

    private ListRow buildLocalRow(int idDB, String filtro) {
        Contenido contenido = ContenidoDB.obtenerContenido(idDB);
        if (contenido == null || contenido.getTitulo() == null
                || !contenido.getTitulo().toLowerCase().contains(filtro)) {
            return null;
        }
        ItemContent item = new ItemContent(contenido.getTitulo(), loadIcon(contenido.getPosterKey()),
                String.valueOf(idDB), Origen.LOCAL);
        return new ListRow(idDB, contenido.getTipoContenido(), null, item);
    }

    private void goToDetailsItem() {
        int row = view.getSelectedRow();
        if (row < 0 || row >= rows.size()) return;
        ListRow r = rows.get(row);
        CompleteItemDialog cid = new CompleteItemDialog(view, true);
        if (r.apiId != null) {
            new CompleteItemController(cid, model, r.apiId, r.tipo);
        } else {
            new CompleteItemController(cid, model, r.idDB, r.tipo);
        }
        cid.setVisible(true);
    }

    private ImageIcon loadIcon(String url) {
        if (url == null) return defaultIcon();
        try {
            return new ImageIcon(new URI(url).toURL());
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException ex) {
            return defaultIcon();
        }
    }

    private ImageIcon defaultIcon() {
        return new ImageIcon(getClass().getResource("/img/defaultPoster.png"));
    }
}
