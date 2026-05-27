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

public class ProfileController extends BaseController<ProfileDialog> {

    public ProfileController(ProfileDialog view, Model model) {
        super(view, model);
        loadProfileData();
        view.setFieldsEditable(false);
        view.setChangeUserButtonText("Configurar");
        view.setCancelButtonListener(cancelButtonListener());
        view.setChangeUserButtonListener(changeUserButtonListener());
        view.setChangePasswordButtonListener(changePasswordButtonListener());
        view.setStatsButtonListener(goToStatsUserButtonActionListener());
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
                    user.setNombre(view.getName());
                    String[] apellidos = view.getAddress().trim().split("\\s+", 2);
                    user.setApellido1(apellidos.length > 0 ? apellidos[0] : "");
                    user.setApellido2(apellidos.length > 1 ? apellidos[1] : "");
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
                String password = JOptionPane.showInputDialog(view, "Introduzca su contraseña", "Cambiar contraseña", JOptionPane.INFORMATION_MESSAGE);
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
                            String newPassword = JOptionPane.showInputDialog(view, "Introduzca su nueva contraseña", "Cambiar contraseña", JOptionPane.INFORMATION_MESSAGE);
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
