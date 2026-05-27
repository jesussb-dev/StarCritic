package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.CsvImport;
import com.starcritic.dam_proyect.data.JasperExport;
import com.starcritic.dam_proyect.data.database.ListaContenidoDB;
import com.starcritic.dam_proyect.data.database.ListaUsuarioDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.ListaUsuario;
import com.starcritic.dam_proyect.view.ListsUserDialog;
import com.starcritic.dam_proyect.view.SearchDialog;
import com.starcritic.dam_proyect.view.UIStyle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controlador del diálogo de listas personales del usuario. Permite crear,
 * importar/exportar (CSV, PDF, Jasper) y eliminar listas, asi como gestionar
 * los contenidos contenidos dentro de ellas.
 * @author Jesús Santos Baquero
 */
public class ListsUserController extends BaseController<ListsUserDialog> {

    public ListsUserController(ListsUserDialog view, Model model) {
        super(view, model);
        addListUser();
        view.addPopupMenuItem(menuItemGoToList());
        view.addPopupMenuItem(menuItemExportList());
        view.setCancelButtonListener(cancelButtonListener());
        view.setAddListButtonListener(addListButtonListener());
        view.setListUserListener(listUserListener());
        view.setSearchButtonListener(searchButtonListener());
        view.setDeleteListListener(deleteListListener());
        view.setImportButtonListener(importButtonListener());
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

    private ActionListener addListButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(view, "Introduce el nombre de la lista", "Crear lista", JOptionPane.INFORMATION_MESSAGE);
                if (name == null || name.trim().isEmpty()) {
                    return;
                }
                ListaUsuario lista = new ListaUsuario(model.getUser().getIdUsuario(), name, LocalDate.now());
                BackgroundWork.run(
                        () -> {
                            ListaUsuarioDB.crearListaUsuario(lista);
                            return null;
                        },
                        v -> {
                            JOptionPane.showMessageDialog(view, "Se ha añadido correctamente", "Creación exitosa", JOptionPane.INFORMATION_MESSAGE);
                            view.addItemList(lista.getNombreLista());
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al crear la lista", "Error", JOptionPane.ERROR_MESSAGE));
            }
        };
        return al;
    }

    private MouseListener listUserListener() {
        MouseAdapter ms = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                view.getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
            }
        };
        return ms;
    }

    private ActionListener searchButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addListUser();
            }
        };
        return al;
    }

    private ActionListener deleteListListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.getSelectedList();
                String nombreLista = view.getSelectedListValue();
                if (nombreLista == null || nombreLista.isEmpty()) {
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(view, "¿Estás seguro de eliminar la lista?", "Eliminar lista", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                BackgroundWork.run(
                        () -> {
                            ListaUsuarioDB.eliminarListaUsuario(model.getUser().getIdUsuario(), nombreLista);
                            return null;
                        },
                        v -> {
                            JOptionPane.showMessageDialog(view, "Se ha borrado la lista correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                            view.deleteItemList(row);
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al eliminar la lista", "Error", JOptionPane.ERROR_MESSAGE));
            }
        };
        return al;
    }

    private ActionListener importButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setBackground(UIStyle.BG_PRIMARY);
                chooser.setFileFilter(new FileNameExtensionFilter("Hoja de cálculo (*.csv)", "csv"));
                if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                File archivo = chooser.getSelectedFile();
                BackgroundWork.run(
                        () -> {
                            String nombre = archivo.getName().substring(0, archivo.getName().lastIndexOf('.'));
                            ListaUsuario lista = new ListaUsuario(model.getUser().getIdUsuario(), nombre, LocalDate.now());
                            ListaUsuarioDB.crearListaUsuario(lista);
                            for (String[] dato : CsvImport.importarCsv(archivo.getAbsolutePath())) {
                                ListaContenidoDB.anadirContenidoALista(model.getUser().getIdUsuario(), nombre, Integer.parseInt(dato[0]));
                            }
                            return null;
                        },
                        v -> {
                            view.clearListUser();
                            addListUser();
                            JOptionPane.showMessageDialog(view, "Se ha importado el archivo correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                        },
                        err -> {
                            if (err instanceof IOException) {
                                JOptionPane.showMessageDialog(view, "Error: no se ha podido importar el archivo", "Operación fallida", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Error: el archivo debe de tener un nombre único", "Operación fallida", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                );
            }
        };
        return al;
    }

    private JMenuItem menuItemGoToList() {
        JMenuItem mi = new JMenuItem("Ver lista...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getSelectedList() != -1) {
                    String nombre = view.getSelectedListValue();
                    if (nombre != null) {
                        SearchDialog sd = new SearchDialog(view, true);
                        new SearchListController(sd, model, nombre);
                        sd.setVisible(true);
                    }
                }
            }
        });
        return mi;
    }

    private JMenuItem menuItemExportList() {
        JMenuItem mi = new JMenuItem("Exportar lista...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.getSelectedList();
                if (row == -1) {
                    return;
                }
                String nombre = view.getSelectedListValue();
                if (nombre == null) {
                    return;
                }
                JComboBox<String> box = new JComboBox<>(new String[]{"csv", "pdf"});
                if (JOptionPane.showConfirmDialog(view, box, "Selecciona el formato",
                        JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    String formato = (String) box.getSelectedItem();
                    BackgroundWork.run(
                            () -> {
                                HashMap<Integer, String> contenidos = ListaContenidoDB.obtenerContenidoPorLista(model.getUser().getIdUsuario(), nombre);
                                new JasperExport(nombre, row, contenidos, formato).exportList();
                                return null;
                            },
                            v -> JOptionPane.showMessageDialog(view, "Se ha exportado el archivo correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE),
                            err -> JOptionPane.showMessageDialog(view, "Error al exportar la lista", "Error", JOptionPane.ERROR_MESSAGE)
                    );
                }
            }
        });
        return mi;
    }

    /**
     * Recargar el listado de listas del usuario en la vista, aplicando el
     * filtro de búsqueda actual.
     */
    public void addListUser() {
        view.clearListUser();
        String filtro = view.getSearchText() != null ? view.getSearchText().toLowerCase().trim() : "";
        BackgroundWork.run(
                () -> ListaUsuarioDB.obtenerListasUsuario(model.getUser().getIdUsuario()),
                listas -> {
                    if (listas == null) {
                        return;
                    }
                    for (ListaUsuario l : listas) {
                        if (filtro.isEmpty() || l.getNombreLista().toLowerCase().contains(filtro)) {
                            view.addItemList(l.getNombreLista());
                        }
                    }
                }, err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista", "Error", JOptionPane.ERROR_MESSAGE));
    }

    /**
     * Añadir un elemento al listado de la vista.
     * @param value el texto a añadir al listado.
     */
    public void addItemToList(String value) {
        view.addItemList(value);
    }
}
