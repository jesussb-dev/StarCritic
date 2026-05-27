package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.UsuarioDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Roles;
import com.starcritic.dam_proyect.view.LogInUserDialog;
import com.starcritic.dam_proyect.view.RegisterDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LogInUserController extends BaseController<LogInUserDialog> {

    private MainNavigationController parent;

    public LogInUserController(LogInUserDialog view, Model model, MainNavigationController parent) {
        super(view, model);
        this.parent = parent;
        view.setLogInButtonListener(logInButtonListener());
        view.setRegisterButtonListener(registerButtonListener());
    }

    private ActionListener logInButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = view.getUserTextField().trim();
                String contrasenha = view.getPasswordTextField().replaceAll("[\\[\\], \\s]+", "").trim();
                BackgroundWork.run(
                        () -> UsuarioDB.verificarLogin(usuario, contrasenha),
                        user -> {
                            if (user == null) {
                                JOptionPane.showMessageDialog(view, "Error: contraseña o usuario incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            if (user.isBaneado()) {
                                JOptionPane.showMessageDialog(view, "Error: este usuario ha sido baneado", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            model.setUser(user);
                            parent.displayUserOptions();
                            if (user.getRol() == Roles.ADMINISTRADOR) {
                                parent.addAllPopudAmdimItems();
                            }
                            parent.addLogOutOption();
                            parent.loadPersonalizedRecommendations();
                            view.dispose();
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error de conexión al iniciar sesión", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private ActionListener registerButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterDialog rd = new RegisterDialog(view, true);
                new RegisterUserController(rd, model);
                rd.setVisible(true);
            }
        };
        return al;
    }
}
