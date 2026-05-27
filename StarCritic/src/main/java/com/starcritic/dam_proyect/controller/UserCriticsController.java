package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.AspectoDB;
import com.starcritic.dam_proyect.data.database.CriticaDB;
import com.starcritic.dam_proyect.data.database.CriticoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Aspecto;
import com.starcritic.dam_proyect.model.pojo.bd.Critica;
import com.starcritic.dam_proyect.model.pojo.itemList.ItemCritic;
import com.starcritic.dam_proyect.view.CriticsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controlador del diálogo "Mis criticas" que lista las criticas hechas por el
 * usuario actual agrupadas por aspecto y permite eliminarlas.
 * @author Jesús Santos Baquero
 */
public class UserCriticsController extends BaseController<CriticsDialog> {

    public UserCriticsController(CriticsDialog view, Model model) {
        super(view, model);
        view.enableAddButton(false);
        cargarAspectos();
        view.clearCriticas();
        view.setAspectListListener(aspectListListener());
        view.setCancelButtonListener(cancelButtonListener());
        view.setDeleteButtonListener(deleteButtonListener());
    }

    private ListSelectionListener aspectListListener() {
        ListSelectionListener lsl = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    cargarCriticasDelAspectoSeleccionado();
                }
            }
        };
        return lsl;
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
                if (row == -1) {
                    return;
                }
                int idCritica = view.getSelectID(row);
                BackgroundWork.run(
                        () -> CriticaDB.esCriticaUsuario(model.getUser().getIdUsuario(), idCritica),
                        esMia -> {
                            if (!esMia) {
                                JOptionPane.showMessageDialog(view, "Error: esta crítica no le pertenece al usuario", "Error de selección", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
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
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error de conexión", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private void cargarAspectos() {
        BackgroundWork.run(
                () -> {
                    List<Aspecto> aspectos = AspectoDB.obtenerAspectosAudiovisual();
                    aspectos.addAll(AspectoDB.obtenerAspectosVideojuego());
                    return aspectos;
                },
                aspectos -> aspectos.forEach(view::addAspectItem),
                err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista", "Error", JOptionPane.ERROR_MESSAGE));

    }

    private void cargarCriticasDelAspectoSeleccionado() {
        view.clearCriticas();
        Aspecto aspecto = view.getSelectedAspect();
        if (aspecto == null) {
            return;
        }
        int idAspecto = aspecto.getIdAspecto();
        int idUsuario = model.getUser().getIdUsuario();
        BackgroundWork.run(
                () -> {
                    List<Critica> criticas = CriticaDB.obtenerCriticasAudiovisualPorUsuario(idAspecto, idUsuario);
                    criticas.addAll(CriticaDB.obtenerCriticasVideojuegoPorUsuario(idAspecto, idUsuario));
                    List<ItemCritic> items = new ArrayList<>();
                    for (Critica c : criticas) {
                        boolean esCritico = CriticoDB.esCritico(c.getIdUsuarioRegistrado());
                        items.add(new ItemCritic(c.getIdCritica(), c.getNombreUsuario(),
                                c.getRol(), c.getDescripcion(), c.getPuntuacion(), esCritico));
                    }
                    return items;
                },
                items -> {
                    if (items.isEmpty()) {
                        view.setErrorText("No hay ninguna crítica");
                    } else {
                        view.setErrorText("");
                        items.forEach(view::addCriticItem);
                    }
                },
                err -> view.setErrorText("Error al cargar las críticas")
        );
    }

}
