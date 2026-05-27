package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.UsuarioDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.view.ListsUserDialog;
import com.starcritic.dam_proyect.view.MessageDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador de la vista de administración de usuarios. Permite banear,
 * desbanear, eliminar y enviar advertencias a los usuarios registrados.
 * @author Jesús Santos Baquero
 */
public class AdminUserController extends BaseController<ListsUserDialog> {

    public AdminUserController(ListsUserDialog view, Model model) {
        super(view, model);
        initComponents();
        view.setCancelButtonListener(cancelButtonListener());
        view.setImportButtonListener(importButtonListener());
        view.setAddListButtonListener(addListButtonListener());
        view.setDeleteListListener(deleteButtonListener());
        view.setListSelectionListener(listSelectionListener());
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

    private ActionListener importButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = extractUserId();
                if (userId == -1) {
                    return;
                }
                MessageDialog md = new MessageDialog(view, true);
                new MessageController(md, model, userId);
                md.setVisible(true);
            }
        };
        return al;
    }

    private ActionListener addListButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = extractUserId();
                if (userId == -1) {
                    return;
                }
                final boolean nuevoEstado = !isSelectedUserBanned();
                BackgroundWork.run(
                        () -> UsuarioDB.banearUsuario(userId, nuevoEstado),
                        success -> {
                            if (success) {
                                JOptionPane.showMessageDialog(view,
                                        nuevoEstado ? "Se ha baneado a este usuario correctamente" : "Se ha desbaneado a este usuario correctamente",
                                        "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                view.clearList();
                                addAllUsers();
                                view.setAddListButtonText("Banear usuario");
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view,
                                nuevoEstado ? "Error al banear al usuario" : "Error al desbanear al usuario",
                                "Error", JOptionPane.ERROR_MESSAGE));
            }
        };
        return al;
    }

    private ActionListener deleteButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = extractUserId();
                if (userId == -1) {
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(view,
                        "¿Seguro que quieres eliminar a este usuario?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                BackgroundWork.run(
                        () -> UsuarioDB.eliminarUsuario(userId),
                        success -> {
                            if (success) {
                                JOptionPane.showMessageDialog(view,
                                        "Usuario eliminado correctamente",
                                        "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                view.clearList();
                                addAllUsers();
                            } else {
                                JOptionPane.showMessageDialog(view,
                                        "No se ha podido eliminar al usuario",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view,
                                "Error al eliminar al usuario",
                                "Error", JOptionPane.ERROR_MESSAGE));
            }
        };
    }

    private javax.swing.event.ListSelectionListener listSelectionListener() {
        return e -> {
            if (!e.getValueIsAdjusting()) {
                view.setAddListButtonText(isSelectedUserBanned() ? "Desbanear" : "Banear usuario");
            }
        };
    }

    private boolean isSelectedUserBanned() {
        String seleccion = view.getSelectedListValue();
        return seleccion != null && seleccion.contains("Baneado: Si");
    }

    private void initComponents() {
        view.setTitle("Usuarios");
        view.setAddListButtonText("Banear usuario");
        view.setImportButtonText("Mandar advertencia");
        addAllUsers();
    }

    private void addAllUsers() {
        BackgroundWork.run(
                () -> UsuarioDB.obtenerTodosLosUsuarios(),
                usuarios -> usuarios.forEach((id, user) -> view.addItemList(user.getNombreUsuario() + " (" + id + ")\n\r"
                + " Baneado: " + (user.isBaneado() ? "Si" : "No"))),
                err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista", "Error", JOptionPane.ERROR_MESSAGE));
    }

    private int extractUserId() {
        String seleccion = view.getSelectedListValue();
        int inicio = seleccion.indexOf('(');
        int fin = seleccion.indexOf(')');
        if (inicio == -1 || fin == -1 || fin <= inicio) {
            return -1;
        }
        try {
            return Integer.parseInt(seleccion.substring(inicio + 1, fin).trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

}
