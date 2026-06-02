package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.model.pojo.bd.SaveListState;
import com.starcritic.dam_proyect.model.pojo.bd.ContentData;
import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.api.rest.ArchivoApi;
import com.starcritic.dam_proyect.data.database.ContenidoDB;
import com.starcritic.dam_proyect.data.database.ContenidoUsuarioDB;
import com.starcritic.dam_proyect.data.database.EtiquetaEditorialDB;
import com.starcritic.dam_proyect.data.database.ListaContenidoDB;
import com.starcritic.dam_proyect.data.database.ListaUsuarioDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.api.OMDbDetailJson;
import com.starcritic.dam_proyect.model.pojo.api.RAWGNameRef;
import com.starcritic.dam_proyect.model.pojo.api.RAWGNormalJson;
import com.starcritic.dam_proyect.model.pojo.api.RAWGPlatformWrapper;
import com.starcritic.dam_proyect.model.pojo.bd.Contenido;
import com.starcritic.dam_proyect.model.pojo.bd.ContenidoAudiovisual;
import com.starcritic.dam_proyect.model.pojo.bd.ContenidoUsuario;
import com.starcritic.dam_proyect.model.pojo.bd.EtiquetaEditorial;
import com.starcritic.dam_proyect.model.pojo.bd.ListaUsuario;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.model.pojo.bd.Videojuego;
import com.starcritic.dam_proyect.view.CompleteItemDialog;
import com.starcritic.dam_proyect.view.CriticsDialog;
import com.starcritic.dam_proyect.view.ListsUserDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controlador del diálogo de detalle de un contenido. Carga la ficha completa
 * desde la API correspondiente (OMDb/RAWG) o desde el backend local y gestiona
 * las acciones de usuario: guardar en lista, escribir critica y visualizar
 * etiquetas/aspectos.
 * @author Jesús Santos Baquero
 */
public class CompleteItemController extends BaseController<CompleteItemDialog> {

    private  String id;
    private int idContenidoLocal;
    private  TipoContenido type;

    public CompleteItemController(CompleteItemDialog view, Model model, String id, TipoContenido type) {
        super(view, model);
        this.id = id;
        this.type = type;
        bindButtons();
        view.enableUserOptions(false);
        loadFromAPI();
    }

    public CompleteItemController(CompleteItemDialog view, Model model, int idContenidoLocal, TipoContenido type) {
        super(view, model);
        this.id = null;
        this.idContenidoLocal = idContenidoLocal;
        this.type = type;
        bindButtons();
        view.enableUserOptions(false);
        loadFromLocal();
    }

    private void bindButtons() {
        view.setSaveInListButton(saveInListButtonListener());
        view.setCriticButton(criticButtonListener());
        view.setCancelButtonListener(cancelButtonListener());
    }

    private ActionListener saveInListButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackgroundWork.run(
                        () -> {
                            List<ListaUsuario> listas = ListaUsuarioDB.obtenerListasUsuario(model.getUser().getIdUsuario());
                            int idContent = (id != null)
                                    ? ContenidoDB.buscarID(id, type)
                                    : idContenidoLocal;
                            boolean hayListaSin = false;
                            for (ListaUsuario lista : listas) {
                                if (!ListaContenidoDB.existeContenidoEnLista(model.getUser().getIdUsuario(), lista.getNombreLista(), idContent)) {
                                    hayListaSin = true;
                                    break;
                                }
                            }
                            return new SaveListState(listas, hayListaSin, idContent);
                        },
                        state -> {
                            if (state.getListas().isEmpty()) {
                                if (JOptionPane.showConfirmDialog(view, "No existe ninguna lista. ¿Quieres crear una?", "Crear lista", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    String newList = JOptionPane.showInputDialog(view, "Introduce el nombre de la lista", "Crear lista", JOptionPane.INFORMATION_MESSAGE);
                                    if (newList != null && !newList.trim().isEmpty()) {
                                        ListaUsuario lista = new ListaUsuario(model.getUser().getIdUsuario(), newList, LocalDate.now());
                                        int idContent = state.getIdContent();
                                        BackgroundWork.run(
                                                () -> {
                                                    ListaUsuarioDB.crearListaUsuario(lista);
                                                    ListaContenidoDB.anadirContenidoALista(model.getUser().getIdUsuario(), newList, idContent);
                                                    return null;
                                                },
                                                v -> JOptionPane.showMessageDialog(view, "Se ha creado la lista y añadido el contenido", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE),
                                                err -> JOptionPane.showMessageDialog(view, "Error al crear la lista", "Error", JOptionPane.ERROR_MESSAGE)
                                        );
                                    }
                                }
                            } else if (state.isHayListaSin()) {
                                ListsUserDialog lug = new ListsUserDialog(view, true);
                                new AddItemToListController(lug, view, model, state.getIdContent(), type);
                                lug.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(view, "No hay ninguna lista existente que no posea este contenido", "Operación fallida", JOptionPane.ERROR_MESSAGE);
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al acceder a las listas", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private ActionListener criticButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id != null) {
                    BackgroundWork.run(
                            () -> ContenidoDB.buscarID(id, type),
                            idContenido -> {
                                CriticsDialog cd = new CriticsDialog(view, true);
                                new CriticsController(cd, model, idContenido, type);
                                cd.setVisible(true);
                            },
                            err -> JOptionPane.showMessageDialog(view, "Error al cargar críticas", "Error", JOptionPane.ERROR_MESSAGE)
                    );
                } else {
                    CriticsDialog cd = new CriticsDialog(view, true);
                    new CriticsController(cd, model, idContenidoLocal, type);
                    cd.setVisible(true);
                }
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

    private void loadFromAPI() {
        BackgroundWork.run(
                () -> {
                    if (type.esAudiovisual()) {
                        OMDbDetailJson item = model.getOMDb().getDetails(id.trim());
                        ContentData data = null;
                        if (item != null) {
                            boolean hasUser = model.getUser() != null;
                            String tags = "";
                            double media = 0;
                            if (hasUser) {
                                ContenidoAudiovisual contenido = new ContenidoAudiovisual(LocalDate.now(), false, false,
                                        item.getTitle(), item.getPlot(), item.getPoster(), type, id);
                                ContenidoDB.registrarContenido(contenido);
                            }
                            int idC = ContenidoDB.buscarID(id, type);
                            if (idC > 0) {
                                if (hasUser) {
                                    registrarVisita(idC);
                                }
                                tags = buildTagsString(idC);
                                media = ContenidoDB.mediaAspectoContenido(idC, 1, type);
                            }
                            data = new ContentData(item.getPoster(), item.getTitle() != null ? item.getTitle() : "", "Valoración: " + media,
                                    item.getGenre() != null ? item.getGenre() : "", item.getPlot() != null ? item.getPlot() : "", tags, hasUser);
                        }
                        return data;

                    } else {
                        RAWGNormalJson item = model.getRAWG().getGame(id);
                        ContentData data = null;
                        if (item != null) {
                            boolean hasUser = model.getUser() != null;
                            String tags = "";
                            double media = 0;
                            String descripcion = resolveDescription(item);
                            if (hasUser) {
                                Videojuego juego = new Videojuego(
                                        LocalDate.now(), false, false,
                                        item.getName(), descripcion, item.getBackgroundImage(), Integer.parseInt(id));
                                ContenidoDB.registrarContenido(juego);
                            }
                            int idC = ContenidoDB.buscarID(id, type);
                            if (idC > 0) {
                                if (hasUser) {
                                    registrarVisita(idC);
                                }
                                tags = buildTagsString(idC);
                                media = ContenidoDB.mediaAspectoContenido(idC, 1, type);
                            }
                            data = new ContentData(item.getBackgroundImage(), item.getName() != null ? item.getName() : "", "Valoración: " + media,
                                    formatGenresAndPlatforms(item), descripcion, tags, hasUser);
                        }
                        return data;
                    }

                },
                data -> applyData(data),
                err -> JOptionPane.showMessageDialog(view, "Error al cargar el contenido", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void loadFromLocal() {
        BackgroundWork.run(
                () -> {
                    Contenido content = ContenidoDB.obtenerContenido(idContenidoLocal);
                    ContentData data = null;
                    if (content != null) {
                        boolean hasUser = model.getUser() != null;
                        if (hasUser) {
                            registrarVisita(idContenidoLocal);
                        }
                        String tags = buildTagsString(idContenidoLocal);
                        double media = ContenidoDB.mediaAspectoContenido(idContenidoLocal, 1, type);
                        data = new ContentData(resolvePosterLocal(content.getPosterKey()), content.getTitulo() != null ? content.getTitulo() : "", "Valoración: " + media, "",
                                content.getSinopsis() != null ? content.getSinopsis() : "", tags, hasUser);
                    }
                    return data;
                },
                data -> applyData(data),
                err -> JOptionPane.showMessageDialog(view, "Error al cargar el contenido", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }

    /** El póster LOCAL se guarda como clave de R2; se firma una URL temporal para mostrarlo. */
    private String resolvePosterLocal(String posterKey) {
        if (posterKey == null || posterKey.isBlank()) {
            return null;
        }
        try {
            return ArchivoApi.urlPresignada(ArchivoApi.Bucket.CONTENIDO_LOCAL, posterKey, 60);
        } catch (Exception ex) {
            return null;
        }
    }

    private void applyData(ContentData data) {
        if (data == null) {
            JOptionPane.showMessageDialog(view, "No se pudo cargar el contenido.", "Error", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            return;
        }
        view.setPosterLabel(data.getPoster());
        view.setTitleLabel(data.getTitle());
        view.setRatingLabel(data.getRatingText());
        view.setGenreLabelText(data.getGenre());
        view.setDescriptionLabelText(data.getDescription());
        view.setTagsLabelText(data.getTags());
        view.enableUserOptions(data.isUserOptionsEnabled());
        view.pack();
        view.setLocationRelativeTo(view.getParent());
    }

    private String buildTagsString(int idContenido) {
        if (idContenido == 0) {
            return "";
        }
        List<EtiquetaEditorial> etiquetas = EtiquetaEditorialDB.obtenerEtiquetasDe(idContenido);
        if (etiquetas == null || etiquetas.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("Etiquetas: ");
        for (int i = 0; i < etiquetas.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(etiquetas.get(i).getNombre());
        }
        return sb.toString();
    }

    private void registrarVisita(int idContenido) {
        ContenidoUsuario visita = new ContenidoUsuario(model.getUser().getIdUsuario(), LocalDate.now(), 1);
        visita.setIdContenido(idContenido);
        ContenidoUsuarioDB.crearContenidoUsuario(visita);
    }

    /**
     * Sinopsis del videojuego a partir del detalle de RAWG. Se prefiere el texto
     * plano ({@code description_raw}); si no está, se usa la versión HTML; si no
     * hay ninguna, una cadena vacía (la sinopsis es obligatoria en BD).
     */
    private String resolveDescription(RAWGNormalJson item) {
        if (item.getDescriptionRaw() != null && !item.getDescriptionRaw().isBlank()) {
            return item.getDescriptionRaw();
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            return item.getDescription();
        }
        return "";
    }

    /**
     * Línea de metadatos de un videojuego (géneros y plataformas), equivalente a
     * la línea de géneros que se muestra para películas y series.
     */
    private String formatGenresAndPlatforms(RAWGNormalJson item) {
        String generos = formatNames("Géneros", item.getGenres());
        String plataformas = formatPlatforms(item.getPlatforms());
        if (generos.isEmpty()) {
            return plataformas;
        }
        if (plataformas.isEmpty()) {
            return generos;
        }
        return generos + "  ·  " + plataformas;
    }

    private String formatNames(String prefix, List<RAWGNameRef> items) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(prefix + ": ");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(items.get(i).getName());
        }
        return sb.toString();
    }

    private String formatPlatforms(List<RAWGPlatformWrapper> platforms) {
        if (platforms == null || platforms.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("Plataformas: ");
        for (int i = 0; i < platforms.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(platforms.get(i).getPlatformName());
        }
        return sb.toString();
    }

}
