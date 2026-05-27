package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.cloudfare.CloudeClient;
import com.starcritic.dam_proyect.data.database.AdminContenidoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Contenido;
import com.starcritic.dam_proyect.model.pojo.bd.Origen;
import com.starcritic.dam_proyect.model.pojo.itemList.ItemContent;
import com.starcritic.dam_proyect.view.AdminContentDialog;
import com.starcritic.dam_proyect.view.ModifyContentDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AdminContentController extends BaseController<AdminContentDialog> {

    private List<Contenido> todosLosContenidos;

    public AdminContentController(AdminContentDialog view, Model model) {
        super(view, model);
        cargarContenidos(null);
        view.setCancelButtonListener(cancelButtonListener());
        view.setAddButtonListener(addButtonListener());
        view.setEditButtonListener(editButtonListener());
        view.setDeleteButtonListener(deleteButtonListener());
        view.setSearchButtonListener(searchButtonListener());
        view.setDestacarButtonListener(destacarButtonListener());
        view.setOcultarButtonListener(ocultarButtonListener());
        view.setListSelectionListener(selectionListener());
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

    private ActionListener addButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyContentDialog mcd = new ModifyContentDialog(view, true);
                new AddContentController(mcd, model);
                mcd.setVisible(true);
                cargarContenidos(null);
            }
        };
        return al;
    }

    private ActionListener editButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(view, "Selecciona un contenido para modificar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ItemContent item = view.getSelectedItem();
                int id = Integer.parseInt(item.getId());
                Contenido contenido = getContenidoById(id);
                if (contenido == null || contenido.getOrigen() != Origen.LOCAL) {
                    JOptionPane.showMessageDialog(view, "Solo se puede modificar contenido de origen LOCAL.", "Operación no permitida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ModifyContentDialog mcd = new ModifyContentDialog(view, true);
                new ModifyContentController(mcd, model, contenido);
                mcd.setVisible(true);
                cargarContenidos(null);
            }
        };
        return al;
    }

    private ActionListener deleteButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(view, "Selecciona un contenido para eliminar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ItemContent item = view.getSelectedItem();
                int id = Integer.parseInt(item.getId());
                Contenido contenido = getContenidoById(id);
                if (contenido == null) {
                    return;
                }

                BackgroundWork.run(
                        () -> AdminContenidoDB.puedeHardDelete(id),
                        puedeHard -> {
                            if (puedeHard) {
                                int confirm = JOptionPane.showConfirmDialog(view,
                                        "¿Eliminar permanentemente \"" + contenido.getTitulo() + "\"?\nEsta acción no se puede deshacer.",
                                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    BackgroundWork.run(
                                            () -> AdminContenidoDB.hardDelete(id, new CloudeClient(CloudeClient.Cubo.CONTENIDO_LOCAL)),
                                            ok -> {
                                                if (ok) {
                                                    JOptionPane.showMessageDialog(view, "Contenido eliminado correctamente.",
                                                            "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                                    cargarContenidos(null);
                                                }
                                            },
                                            err -> JOptionPane.showMessageDialog(view, "Error al eliminar el contenido",
                                                    "Error", JOptionPane.ERROR_MESSAGE)
                                    );
                                }
                            } else {
                                int confirm = JOptionPane.showConfirmDialog(view, "\"" + contenido.getTitulo() + "\" tiene referencias asociadas.\n¿Deseas ocultarlo en lugar de eliminarlo?",
                                        "Confirmar ocultación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    BackgroundWork.run(
                                            () -> AdminContenidoDB.softDelete(id),
                                            ok -> {
                                                if (ok) {
                                                    JOptionPane.showMessageDialog(view, "Contenido ocultado correctamente.", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                                    cargarContenidos(null);
                                                }
                                            },
                                            err -> JOptionPane.showMessageDialog(view, "Error al ocultar el contenido", "Error", JOptionPane.ERROR_MESSAGE)
                                    );
                                }
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al verificar el contenido", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private ActionListener searchButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = view.getSearchText().trim();
                cargarContenidos(filtro.isEmpty() ? null : filtro);
            }
        };
        return al;
    }

    private ActionListener destacarButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(view, "Selecciona un contenido para destacar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ItemContent item = view.getSelectedItem();
                int id = Integer.parseInt(item.getId());
                Contenido contenido = getContenidoById(id);
                if (contenido == null) {
                    return;
                }
                boolean nuevoValor = !contenido.isDestacado();
                BackgroundWork.run(
                        () -> AdminContenidoDB.actualizarDestacado(id, nuevoValor),
                        ok -> {
                            if (ok) {
                                JOptionPane.showMessageDialog(view,
                                        nuevoValor ? "Contenido destacado correctamente." : "Contenido normalizado correctamente.",
                                        "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                cargarContenidos(null);
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al actualizar el contenido", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private ActionListener ocultarButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(view, "Selecciona un contenido para ocultar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ItemContent item = view.getSelectedItem();
                int id = Integer.parseInt(item.getId());
                Contenido contenido = getContenidoById(id);
                if (contenido == null) {
                    return;
                }
                boolean nuevoValor = !contenido.isOculto();
                BackgroundWork.run(
                        () -> AdminContenidoDB.actualizarOculto(id, nuevoValor),
                        ok -> {
                            if (ok) {
                                JOptionPane.showMessageDialog(view,
                                        nuevoValor ? "Contenido ocultado correctamente." : "Contenido normalizado correctamente.",
                                        "Operación exitiosa", JOptionPane.INFORMATION_MESSAGE);
                                cargarContenidos(null);
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al actualizar el contenido", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private ListSelectionListener selectionListener() {
        ListSelectionListener lsl = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    actualizarTextosBotones();
                }
            }
        };
        return lsl;
    }

    private void actualizarTextosBotones() {
        Contenido contenido = null;
        if (view.getSelectedIndex() != -1) {
            contenido = getContenidoById(Integer.parseInt(view.getSelectedItem().getId()));
        }
        if (contenido == null) {
            view.setDestacarButtonText("Destacar");
            view.setOcultarButtonText("Ocultar");
            return;
        }
        view.setDestacarButtonText(contenido.isDestacado() ? "Normalizar" : "Destacar");
        view.setOcultarButtonText(contenido.isOculto() ? "Normalizar" : "Ocultar");
    }

    private void cargarContenidos(String filtro) {
        BackgroundWork.run(
                () -> {
                    List<Contenido> lista = AdminContenidoDB.obtenerCatalogoCompleto();
                    List<ItemContent> items = new ArrayList<>();
                    for (Contenido c : lista) {
                        if (filtro == null || c.getTitulo().toLowerCase().contains(filtro.toLowerCase())) {
                            items.add(buildItemContent(c));
                        }
                    }
                    return Map.entry(lista, items);
                },
                entry -> {
                    todosLosContenidos = entry.getKey();
                    view.clearList();
                    for (ItemContent item : entry.getValue()) {
                        view.addElementSearch(item);
                    }
                },
                err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista", "Error", JOptionPane.ERROR_MESSAGE));
    }

    private ItemContent buildItemContent(Contenido c) {
        String label = c.getTitulo() + "  [" + c.getTipoContenido() + " · " + c.getOrigen() + "]"
                + (c.isOculto() ? "  (oculto)" : "") + (c.isDestacado() ? "  ★" : "");
        return new ItemContent(label, loadPoster(c.getPosterKey()), String.valueOf(c.getIdContenido()), c.getOrigen());
    }

    private ImageIcon loadPoster(String url) {
        if (url != null && !url.isBlank()) {
            try {
                return new ImageIcon(new URI(url).toURL());
            } catch (URISyntaxException | MalformedURLException | IllegalArgumentException ignored) {
            }
        }
        return new ImageIcon(getClass().getResource("/img/defaultPoster.png"));
    }

    private Contenido getContenidoById(int id) {
        if (todosLosContenidos == null) {
            return null;
        }
        return todosLosContenidos.stream().filter(c -> c.getIdContenido() == id).findFirst().orElse(null);
    }

}
