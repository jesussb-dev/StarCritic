package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.AspectoDB;
import com.starcritic.dam_proyect.data.database.ContenidoDB;
import com.starcritic.dam_proyect.data.database.CriticaDB;
import com.starcritic.dam_proyect.data.database.CriticoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Aspecto;
import com.starcritic.dam_proyect.model.pojo.bd.Critica;
import com.starcritic.dam_proyect.model.pojo.bd.MediaConItems;
import com.starcritic.dam_proyect.model.pojo.bd.Roles;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.model.pojo.itemList.ItemCritic;
import com.starcritic.dam_proyect.view.CriticDialog;
import com.starcritic.dam_proyect.view.CriticsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CriticsController extends BaseController<CriticsDialog> {

    private  TipoContenido type;
    private  int idContenido;
    private  boolean esAudiovisual;

    public CriticsController(CriticsDialog view, Model model, int idContenido, TipoContenido type) {
        super(view, model);
        this.idContenido = idContenido;
        this.type = type;
        this.esAudiovisual = type.esAudiovisual();
        view.enableModifyButton(false);
        cargarAspectos();
        view.clearCriticas();
        view.aspectListMouseListener(aspectListMouseListener());
        view.setAddButtonListener(addButtonListener());
        view.setCancelButtonListener(cancelButtonListener());
        view.setDeleteButtonListener(deleteButtonListener());
    }

    private MouseListener aspectListMouseListener() {
        MouseAdapter ms = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarCriticasDelAspectoSeleccionado();
            }

        };
        return ms;
    }

    private ActionListener addButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriticDialog cd = new CriticDialog(view, true);
                new CriticController(cd, type, model, view.getSelectedAspect().getIdAspecto(), idContenido, CriticsController.this);
                cd.setVisible(true);
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

    private ActionListener deleteButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.getSelectedCritic();
                if (row != -1) {
                    int idCritica = view.getSelectID(row);
                    if (model.getUser().getRol() != Roles.ESTANDAR) {
                        confirmAndDelete(idCritica);
                    } else {
                        BackgroundWork.run(
                                () -> CriticaDB.esCriticaUsuario(model.getUser().getIdUsuario(), idCritica),
                                esMia -> {
                                    if (esMia) {
                                        confirmAndDelete(idCritica);
                                    } else {
                                        JOptionPane.showMessageDialog(view,
                                                "Error: esta crítica no le pertenece al usuario",
                                                "Error de selección", JOptionPane.ERROR_MESSAGE);
                                    }
                                },
                                err -> JOptionPane.showMessageDialog(view, "Error de conexión", "Error", JOptionPane.ERROR_MESSAGE)
                        );
                    }
                }
            }
        };
        return al;
    }

    private void cargarAspectos() {
        BackgroundWork.run(
                () -> {
                    List<Aspecto> aspectos = null;
                    if (esAudiovisual) {
                        aspectos = AspectoDB.obtenerAspectosAudiovisual();
                    } else {
                        aspectos = AspectoDB.obtenerAspectosVideojuego();
                    }
                    return aspectos;
                },
                aspectos -> {
                    for (Aspecto aspecto : aspectos) {
                        view.addAspectItem(aspecto);
                    }
                },
                err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista", "Error", JOptionPane.ERROR_MESSAGE));

    }

    private void confirmAndDelete(int idCritica) {
        int confirm = JOptionPane.showConfirmDialog(view, "¿Estás seguro de eliminar la crítica?", "Eliminar crítica", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            BackgroundWork.run(
                    () -> {
                        CriticaDB.eliminarCritica(idCritica);
                        return null;
                    },
                    v -> JOptionPane.showMessageDialog(view, "Se ha borrado correctamente la crítica", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE),
                    err -> JOptionPane.showMessageDialog(view, "Error al eliminar la crítica", "Error", JOptionPane.ERROR_MESSAGE)
            );
        }
    }

    public void cargarCriticasDelAspectoSeleccionado() {
        view.clearCriticas();
        Aspecto aspecto = view.getSelectedAspect();
        if (aspecto != null) {
            view.enableModifyButton(true);
            int idAspecto = aspecto.getIdAspecto();
            BackgroundWork.run(
                    () -> {
                        double media = ContenidoDB.mediaAspectoContenido(idContenido, idAspecto, type);
                        List<Critica> criticas = esAudiovisual
                                ? CriticaDB.obtenerCriticasAudiovisualPorAspecto(idAspecto, idContenido)
                                : CriticaDB.obtenerCriticasVideojuegoPorAspecto(idAspecto, idContenido);
                        List<ItemCritic> items = new ArrayList<>();
                        if (criticas != null) {
                            for (Critica c : criticas) {
                                boolean esCritico = CriticoDB.esCritico(c.getIdUsuarioRegistrado());
                                items.add(new ItemCritic(c.getIdCritica(), c.getNombreUsuario(),
                                        c.getRol(), c.getDescripcion(), c.getPuntuacion(), esCritico));
                            }
                        }
                        return new MediaConItems(items, media);
                    },
                    datos -> {
                        if (datos.getItems().isEmpty()) {
                            view.setErrorText("No hay ninguna crítica");
                        } else {
                            view.setErrorText("Valoración: " + datos.getMedia());
                            datos.getItems().forEach(view::addCriticItem);
                        }
                    },
                    err -> view.setErrorText("Error al cargar las críticas")
            );
        }

    }
}
