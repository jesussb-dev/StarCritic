package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.cloudfare.CloudeClient;
import com.starcritic.dam_proyect.data.database.AdminContenidoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Contenido;
import com.starcritic.dam_proyect.model.pojo.bd.Origen;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.view.ModifyContentDialog;
import com.starcritic.dam_proyect.view.UIStyle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Controlador del diálogo de creación de contenido LOCAL. Gestiona la selección
 * de imagen del póster, validación y alta del contenido en el backend a través
 * de {@link AdminContenidoDB}.
 * @author Jesús Santos Baquero
 */
public class AddContentController extends BaseController<ModifyContentDialog> {

    public AddContentController(ModifyContentDialog view, Model model) {
        super(view, model);
        initComponents();
        view.setImageButtonListener(imageButtonListener());
        view.setCancelButtonListener(cancelButtonListener());
        view.setConfirmButtonListener(confirmButtonListener());
        view.pack();
    }

    private ActionListener imageButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setBackground(UIStyle.BG_PRIMARY);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    view.setImageStatusLabelText(chooser.getSelectedFile().getAbsolutePath());
                }
                view.pack();
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

    private ActionListener confirmButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isValid()) {
                    JOptionPane.showMessageDialog(view, "El título y la sinopsis no pueden estar vacíos.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TipoContenido type = view.getSelectionComboBoxValue();
                String title = view.getTitleText().trim();
                String sinopsis = view.getTextAreaText().trim();
                String imagePath = view.getImageStatusLabelText();

                File posterFile = null;
                if (imagePath != null && !imagePath.isBlank()) {
                    File f = new File(imagePath);
                    if (f.exists()) {
                        posterFile = f;
                    }
                }
                final File finalPosterFile = posterFile;
                Contenido contenido = new Contenido(LocalDate.now(), Origen.LOCAL, false, false, title, sinopsis, null, type);

                BackgroundWork.run(
                        () -> AdminContenidoDB.crearContenidoLocal(new CloudeClient(CloudeClient.Cubo.CONTENIDO_LOCAL), contenido, finalPosterFile),
                        success -> {
                            if (success) {
                                JOptionPane.showMessageDialog(view, "Contenido creado correctamente.", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                view.dispose();
                            } else {
                                JOptionPane.showMessageDialog(view, "No se pudo crear el contenido.", "Error de inserción", JOptionPane.ERROR_MESSAGE);
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view, "No se pudo crear el contenido.", "Error", JOptionPane.ERROR_MESSAGE));
            }
        };
        return al;
    }

    private void initComponents() {
        view.setTitleLabelText("CREACIÓN DE CONTENIDO");
        view.setConfirmButtonText("Añadir");
        view.setTitle("Añadir contenido");
    }

    private boolean isValid() {
        String title = view.getTitleText();
        String sinopsis = view.getTextAreaText();
        return title != null && !title.trim().isEmpty() && sinopsis != null && !sinopsis.trim().isEmpty();
    }
}
