package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.UsuarioDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.UsuarioRegistrado;
import com.starcritic.dam_proyect.view.ProfileDialog;
import com.starcritic.dam_proyect.view.StatsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class ProfileController extends BaseController<ProfileDialog> {

    private final MainNavigationController parent;

    public ProfileController(ProfileDialog view, Model model, MainNavigationController parent) {
        super(view, model);
        this.parent = parent;
        loadProfileData();
        view.setFieldsEditable(false);
        view.setChangeUserButtonText("Configurar");
        view.setCancelButtonListener(cancelButtonListener());
        view.setChangeUserButtonListener(changeUserButtonListener());
        view.setChangePasswordButtonListener(changePasswordButtonListener());
        view.setStatsButtonListener(goToStatsUserButtonActionListener());
        view.setEliminarButtonListener(eliminarButtonListener());
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

    private ActionListener changeUserButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getButtonConfigurarText().equals("Configurar")) {
                    view.setFieldsEditable(true);
                    view.setChangeUserButtonText("Guardar");
                    view.setResultLabel("");
                } else {
                    if (!checkProfile()) {
                        JOptionPane.showMessageDialog(view, "Algunos de los campos es incorrecto", "Error de usuario", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    UsuarioRegistrado user = (UsuarioRegistrado) model.getUser();
                    user.setNombreUsuario(view.getUser());
                    // El campo "Nombre" contiene el nombre completo (nombre + apellidos);
                    // se reparte en nombre/apellido1/apellido2. El campo "Dirección"
                    // contiene el correo electrónico.
                    String[] partes = view.getName().trim().split("\\s+", 3);
                    user.setNombre(partes.length > 0 ? partes[0] : "");
                    user.setApellido1(partes.length > 1 ? partes[1] : "");
                    user.setApellido2(partes.length > 2 ? partes[2] : "");
                    user.setCorreoElectronico(view.getAddress().trim());
                    BackgroundWork.run(
                            () -> UsuarioDB.modificarUsuarioRegistrado(user),
                            success -> {
                                if (success) {
                                    JOptionPane.showMessageDialog(view, "Datos actualizados correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                    view.setResultLabel("Modificación realizada");
                                    view.setFieldsEditable(false);
                                    view.setChangeUserButtonText("Configurar");
                                } else {
                                    JOptionPane.showMessageDialog(view, "No se pudieron actualizar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            },
                            err -> JOptionPane.showMessageDialog(view, "Error de conexión", "Error", JOptionPane.ERROR_MESSAGE)
                    );
                }
            }
        };
        return al;
    }

    private ActionListener goToStatsUserButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatsDialog usd = new StatsDialog(view, true);
                StatsUserController suc = new StatsUserController(usd, model);
                usd.setVisible(true);
            }
        };
        return al;
    }

    private ActionListener changePasswordButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = promptPassword("Introduzca su contraseña", "Cambiar contraseña", JOptionPane.INFORMATION_MESSAGE);
                if (password == null) {
                    return;
                }
                BackgroundWork.run(
                        () -> UsuarioDB.verificarContrasenha(model.getUser().getIdUsuario(), password),
                        valid -> {
                            if (!valid) {
                                JOptionPane.showMessageDialog(view, "Error: la contraseña no es correcta", "Error de verificación", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            String newPassword = promptPassword("Introduzca su nueva contraseña", "Cambiar contraseña", JOptionPane.INFORMATION_MESSAGE);
                            if (newPassword == null) {
                                return;
                            }
                            BackgroundWork.run(
                                    () -> UsuarioDB.modificarContrasenha(model.getUser().getIdUsuario(), newPassword),
                                    ok -> {
                                        if (ok) {
                                            JOptionPane.showMessageDialog(view, "Datos actualizados correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(view, "Error: no se han podido actualizar los datos", "Error de modificación", JOptionPane.ERROR_MESSAGE);
                                        }
                                    },
                                    err -> JOptionPane.showMessageDialog(view, "Error de conexión",
                                            "Error", JOptionPane.ERROR_MESSAGE)
                            );
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error de conexión",
                                "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private ActionListener eliminarButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = promptPassword("Introduzca su contraseña", "Eliminar usuario", JOptionPane.WARNING_MESSAGE);
                if (password == null) {
                    return;
                }
                BackgroundWork.run(
                        () -> UsuarioDB.verificarContrasenha(model.getUser().getIdUsuario(), password),
                        valid -> {
                            if (!valid) {
                                JOptionPane.showMessageDialog(view, "Error: la contraseña no es correcta", "Error de verificación", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            int confirm = JOptionPane.showConfirmDialog(view, "¿Está seguro de que desea eliminar su cuenta? Esta acción no se puede deshacer.", "Eliminar usuario", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (confirm != JOptionPane.YES_OPTION) {
                                return;
                            }
                            BackgroundWork.run(
                                    () -> UsuarioDB.eliminarUsuario(model.getUser().getIdUsuario()),
                                    ok -> {
                                        if (ok) {
                                            JOptionPane.showMessageDialog(view, "Cuenta eliminada correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                            parent.logOut();
                                            view.dispose();
                                        } else {
                                            JOptionPane.showMessageDialog(view, "Error: no se ha podido eliminar la cuenta", "Error de eliminación", JOptionPane.ERROR_MESSAGE);
                                        }
                                    },
                                    err -> JOptionPane.showMessageDialog(view, "Error de conexión",
                                            "Error", JOptionPane.ERROR_MESSAGE)
                            );
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error de conexión",
                                "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private String promptPassword(String message, String title, int messageType) {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(view, new Object[]{message, passwordField}, title, JOptionPane.OK_CANCEL_OPTION, messageType);
        if (option != JOptionPane.OK_OPTION) {
            return null;
        }
        return new String(passwordField.getPassword());
    }

    private void loadProfileData() {
        if (model.getUser() instanceof UsuarioRegistrado user) {
            view.setUser(user.getNombreUsuario());
            String ap1 = user.getApellido1() != null ? user.getApellido1() : "";
            String ap2 = user.getApellido2() != null ? user.getApellido2() : "";
            view.setName(user.getNombre() + " " + ap1 + " " + ap2.trim());
            view.setAddress(user.getCorreoElectronico());
        }
    }

    private boolean checkProfile() {
        return view.getUser() != null && !view.getUser().trim().isEmpty()
                && view.getName() != null && !view.getName().trim().isEmpty()
                && view.getAddress() != null && !view.getAddress().trim().isEmpty();
    }
}
