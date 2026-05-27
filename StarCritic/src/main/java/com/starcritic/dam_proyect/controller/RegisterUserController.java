package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.UsuarioDB;
import com.starcritic.dam_proyect.exception.ExceptionBadEmailFormatted;
import com.starcritic.dam_proyect.exception.ValidateEmail;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Roles;
import com.starcritic.dam_proyect.model.pojo.bd.UsuarioRegistrado;
import com.starcritic.dam_proyect.view.RegisterDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class RegisterUserController extends BaseController<RegisterDialog> {

    public RegisterUserController(RegisterDialog view, Model model) {
        super(view, model);
        view.setCancelButtonListener(cancelButtonListener());
        view.setSaveButtonListener(saveButtonListener());
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

    private ActionListener saveButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkRegister()) {
                    JOptionPane.showMessageDialog(view, "Algunos de los campos es incorrecto",
                            "Error de usuario", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] apellidos = view.getSecondName().trim().split("\\s+");
                UsuarioRegistrado user = new UsuarioRegistrado(view.getNameUser().trim(), view.getEmail(), LocalDate.now(), view.getName().trim(),
                        apellidos[0], apellidos.length > 1 ? apellidos[1] : null, Roles.ESTANDAR, view.getRepeatPassword().replaceAll("[\\[\\], \\s]+", "").trim(), false);
                BackgroundWork.run(
                        () -> UsuarioDB.insertarUsuarioRegistrado(user),
                        success -> {
                            if (success) {
                                JOptionPane.showMessageDialog(view, "Se ha creado el usuario correctamente", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                view.dispose();
                            } else {
                                JOptionPane.showMessageDialog(view, "Error al crear el usuario. Posibles causas: usuario o correo ya existente o email mal forzfa", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al crear el usuario", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private boolean checkRegister() {
        return !isBlank(view.getNameUser())
                && !isBlank(view.getName())
                && !isBlank(view.getSecondName())
                && !isBlank(view.getEmail())
                && !isBlank(view.getPassword())
                && !isBlank(view.getRepeatPassword());
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
