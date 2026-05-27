package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.cloudfare.CloudeClient;
import com.starcritic.dam_proyect.data.database.AdminContenidoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Contenido;
import com.starcritic.dam_proyect.view.ModifyContentDialog;
import com.starcritic.dam_proyect.view.UIStyle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ModifyContentController extends BaseController<ModifyContentDialog> {

    private Contenido contenido;

    public ModifyContentController(ModifyContentDialog view, Model model, Contenido contenido) {
        super(view, model);
        this.contenido = contenido;
        initComponents();
        view.setImageButtonListener(imageButtonListener());
        view.setCancelButtonListener(cancelButtonListener());
        view.setConfirmButtonListener(confirmButtonListener());
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
                String title = view.getTitleText();
                if (title == null || title.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "El título no puede estar vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                contenido.setTitulo(title.trim());
                contenido.setSinopsis(view.getTextAreaText());

                String imagePath = view.getImageStatusLabelText();
                File posterFile = null;
                if (imagePath != null && !imagePath.isBlank()) {
                    posterFile = new File(imagePath);
                    if (!posterFile.exists()) {
                        posterFile = null;
                    }
                }
                final File finalPosterFile = posterFile;

                CloudeClient cloud = null;
                if (finalPosterFile != null) {
                    cloud = new CloudeClient(CloudeClient.Cubo.CONTENIDO_LOCAL);
                }
                final CloudeClient cloudFinal = cloud;

                BackgroundWork.run(
                        () -> {
                            return AdminContenidoDB.actualizarContenidoLocal(cloudFinal, contenido, finalPosterFile);
                        },
                        success -> {
                            if (success) {
                                JOptionPane.showMessageDialog(view, "Contenido actualizado correctamente.", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                                view.dispose();
                            } else {
                                JOptionPane.showMessageDialog(view, "No se pudo actualizar el contenido.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        },
                        err -> JOptionPane.showMessageDialog(view, "No se pudo actualizar el contenido.", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private void initComponents() {
        view.setTitle("Modificar contenido");
        view.setTitleLabelText("MODIFICAR CONTENIDO");
        view.setConfirmButtonText("Guardar");
        view.setTitleText(contenido.getTitulo());
        view.setSynopsisText(contenido.getSinopsis() != null ? contenido.getSinopsis() : "");
        view.setContentType(contenido.getTipoContenido());
        view.disableContentComboBox();
    }

}
