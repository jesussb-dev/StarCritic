package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.CriticaDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.CriticaAudiovisual;
import com.starcritic.dam_proyect.model.pojo.bd.CriticaVideojuego;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.view.CriticDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador del diálogo de creación de una critica sobre un aspecto concreto
 * de un contenido. Distingue entre critica audiovisual y de videojuego.
 * @author Jesús Santos Baquero
 */
public class CriticController extends BaseController<CriticDialog> {

    private  TipoContenido type;
    private  int idAspecto;
    private  int idContenido;
    private  CriticsController parent;

    public CriticController(CriticDialog view, TipoContenido type, Model model, int idAspecto, int idContenido, CriticsController parent) {
        super(view, model);
        this.type = type;
        this.idAspecto = idAspecto;
        this.idContenido = idContenido;
        this.parent = parent;
        view.setSaveButtonListener(saveButtonListener());
        view.setCancelButtonListener(cancelButtonListener());
    }

    private ActionListener saveButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rating;
                try {
                    rating = view.getSpinnerValue();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "La puntuación debe ser un número entero.", "Puntuación inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (rating < 0 || rating > 100) {
                    JOptionPane.showMessageDialog(view, "La puntuación debe estar entre 0 y 100.", "Puntuación fuera de rango", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String description = view.getDescriptionText();
                if (description == null || description.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "La descripción no puede estar vacía.", "Descripción inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int userId = model.getUser().getIdUsuario();
                final int finalRating = rating;
                BackgroundWork.run(
                        () -> {
                            if (type.esAudiovisual()) {
                                CriticaDB.registrarCriticaAudiovisual(new CriticaAudiovisual(finalRating, description, userId, idAspecto, idContenido));
                            } else {
                                CriticaDB.registrarCriticaVideojuego(new CriticaVideojuego(finalRating, description, userId, idAspecto, idContenido));
                            }
                            return null;
                        },
                        v -> {
                            parent.cargarCriticasDelAspectoSeleccionado();
                            view.dispose();
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al guardar la crítica.",
                                "Error", JOptionPane.ERROR_MESSAGE)
                );
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

}
