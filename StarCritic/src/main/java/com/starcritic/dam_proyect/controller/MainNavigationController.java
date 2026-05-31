package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.CriticoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Critico;
import com.starcritic.dam_proyect.model.pojo.bd.EstadoCertificacion;
import com.starcritic.dam_proyect.model.pojo.bd.UsuarioRegistrado;
import com.starcritic.dam_proyect.view.AdminContentDialog;
import com.starcritic.dam_proyect.view.CriticsDialog;
import com.starcritic.dam_proyect.view.ListsUserDialog;
import com.starcritic.dam_proyect.view.LogInUserDialog;
import com.starcritic.dam_proyect.view.MainNavigationFrame;
import com.starcritic.dam_proyect.view.MessagesDialog;
import com.starcritic.dam_proyect.view.ProfileDialog;
import com.starcritic.dam_proyect.view.SearchDialog;
import com.starcritic.dam_proyect.view.StatsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Controlador de la ventana principal de la aplicación. Coordina la navegación
 * entre las pantallas de búsqueda, perfil, listas, criticas, administración y
 * estadisticas, gestionando el menú emergente del usuario.
 * @author Jesús Santos Baquero
 */
public class MainNavigationController extends BaseController<MainNavigationFrame> {

    private RecommendationController recommendationCtrl;

    public MainNavigationController(Model model, MainNavigationFrame view) {
        super(view, model);
        recommendationCtrl = new RecommendationController(view, model);
        recommendationCtrl.load(-1);
        view.enableLogIn(true);
        view.enableUserOptions(false);
        view.setSearchButtonListener(searchButtonListener());
        view.setLogInButtonListener(logInButtonListener());
        view.setUserButtonListener(userButtonListener());
        view.setStatsButtonListener(getStatsButtonActionListener());
        view.setMessageButtonListener(getMessageButtonActionListener());
    }

    private ActionListener searchButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchDialog sd = new SearchDialog(view, true);
                new SearchController(sd, model);
                sd.setVisible(true);
            }
        };
        return al;
    }

    private ActionListener logInButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogInUserDialog lid = new LogInUserDialog(view, true);
                new LogInUserController(lid, model, MainNavigationController.this);
                lid.setVisible(true);
            }
        };
        return al;
    }

    private ActionListener userButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getPopupMenu().show(view.getUserButton(), 0, view.getUserButton().getHeight());
            }
        };
        return al;
    }

    /**
     * Cargar las recomendaciones personalizadas para el usuario actual. Si no
     * hay usuario en sesión no hace nada.
     */
    public void loadPersonalizedRecommendations() {
        if (model.getUser() != null) {
            recommendationCtrl.load(model.getUser().getIdUsuario());
        }
    }

    /**
     * Cargar las recomendaciones globales (no personalizadas).
     */
    public void loadGlobalRecommendations() {
        recommendationCtrl.load(-1);
    }

    private JMenuItem addPerfilItem() {
        JMenuItem mi = new JMenuItem("Perfil...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfileDialog pd = new ProfileDialog(view, true);
                new ProfileController(pd, model);
                pd.setVisible(true);
            }
        });
        return mi;
    }

    private JMenuItem addCriticasItem() {
        JMenuItem mi = new JMenuItem("Mis críticas...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriticsDialog cd = new CriticsDialog(view, true);
                new UserCriticsController(cd, model);
                cd.setVisible(true);
            }
        });
        return mi;
    }

    private JMenuItem addListasItem() {
        JMenuItem mi = new JMenuItem("Mis listas...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListsUserDialog lud = new ListsUserDialog(view, true);
                new ListsUserController(lud, model);
                lud.setVisible(true);
            }
        });
        return mi;
    }

    private JMenuItem addMandarSolicitudItem() {
        JMenuItem mi = new JMenuItem("Mandar solicitud de crítico...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackgroundWork.run(
                        () -> {
                            if (CriticoDB.esCritico(model.getUser().getIdUsuario())) {
                                Critico critico = CriticoDB.obtenerCritico(model.getUser().getIdUsuario());
                                if (EstadoCertificacion.PENDIENTE.equals(critico.getEstado())) {
                                    return "BLOCKED";
                                }else if(EstadoCertificacion.ACEPTADA.equals(critico.getEstado())) {
                                    return "DONE";
                                }
                            } else {
                                CriticoDB.anhadirCritico(model.getUser().getIdUsuario(), "", EstadoCertificacion.NO_SOLICITADA);
                            }
                            return "OK";
                        },
                        result -> {
                            if ("BLOCKED".equals(result)) {
                                JOptionPane.showMessageDialog(view, "Ya está en proceso de validación su certificación", "Operación rechazada", JOptionPane.ERROR_MESSAGE);
                            }else if("DONE".equals(result)){
                                JOptionPane.showMessageDialog(view, "Ya se ha válidado este usuario", "Operación rechazada", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JFileChooser chooser = new JFileChooser();
                                new FileChooserController(chooser, model);
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al procesar la solicitud", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        });
        return mi;
    }

    /**
     * Mostrar en el menú emergente las opciones disponibles para el usuario
     * autenticado (perfil, criticas, listas y solicitud de critico).
     */
    public void displayUserOptions() {
        view.clearPopupMenu();
        view.setLabelUser(((UsuarioRegistrado) model.getUser()).getNombreUsuario());
        view.enableLogIn(false);
        view.addItemPopupMenu(this.addPerfilItem());
        view.addItemPopupMenu(this.addCriticasItem());
        view.addItemPopupMenu(this.addListasItem());
        view.addItemPopupMenu(this.addMandarSolicitudItem());
        view.enableUserOptions(true);

    }

    private JMenuItem addAdministrarUsuariosItem() {
        JMenuItem mi = new JMenuItem("Administrar Usuarios...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListsUserDialog lud = new ListsUserDialog(view, true);
                new AdminUserController(lud, model);
                lud.setVisible(true);
            }
        });
        return mi;
    }

    private JMenuItem addRevisarSolicitudesItem() {
        JMenuItem mi = new JMenuItem("Revisar solicitudes de crítico...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchDialog sd = new SearchDialog(view, true);
                new RevisionCertificationsController(sd, model);
                sd.setVisible(true);
            }
        });
        return mi;
    }

    private JMenuItem addAdministrarContenidoItem() {
        JMenuItem mi = new JMenuItem("Administrar Contenidos...");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminContentDialog acd = new AdminContentDialog(view, true);
                new AdminContentController(acd, model);
                acd.setVisible(true);
            }
        });
        return mi;
    }

    /**
     * Añadir al menú emergente las opciones de administración (usuarios,
     * contenidos y solicitudes de critico).
     */
    public void addAllPopudAmdimItems() {
        view.addItemPopupMenu(this.addAdministrarUsuariosItem());
        view.addItemPopupMenu(this.addAdministrarContenidoItem());
        view.addItemPopupMenu(this.addRevisarSolicitudesItem());
    }

    /**
     * Construir la opción de menú para cerrar la sesión actual.
     * @return el {@link JMenuItem} con el listener de cierre de sesión.
     */
    public JMenuItem addLogOutItem() {
        JMenuItem mi = new JMenuItem("Cerrar Sesión");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setUser(null);
                view.clearPopupMenu();
                view.enableLogIn(true);
                view.enableUserOptions(false);
                loadGlobalRecommendations();
            }
        });
        return mi;
    }

    /**
     * Añadir al menú emergente la opción de cerrar sesión.
     */
    public void addLogOutOption() {
        view.addItemPopupMenu(this.addLogOutItem());
    }
    
    private ActionListener getStatsButtonActionListener(){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                StatsDialog sd = new StatsDialog(view,true);
                StatsController sc = new StatsController(sd, model);
                sd.setVisible(true);
            }
            
        };
        return al;
    }
    private ActionListener getMessageButtonActionListener(){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MessagesDialog md = new MessagesDialog(view,true);
                new MessageRecieveController(md,model);
                md.setVisible(true);
            }
            
        };
        return al;
    }
}
