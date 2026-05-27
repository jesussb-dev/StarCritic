package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.cloudfare.CloudeClient;
import com.starcritic.dam_proyect.data.database.CriticoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Critico;
import com.starcritic.dam_proyect.model.pojo.bd.EstadoCertificacion;
import com.starcritic.dam_proyect.view.UIStyle;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Controlador del diálogo de selección de archivo para subir la certificación
 * de un critico. Sube el PDF a Cloudflare R2 y deja la certificación en estado
 * PENDIENTE para su revisión.
 * @author Jesús Santos Baquero
 */
public class FileChooserController {

    private  JFileChooser chooser;
    private  Model model;

    public FileChooserController(JFileChooser chooser, Model model) {
        this.chooser = chooser;
        this.model = model;
        chooser.setBackground(UIStyle.BG_PRIMARY);
        manageFile();
    }

    private void manageFile() {
        int resultado = chooser.showOpenDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) return;
        File archivo = chooser.getSelectedFile();
        BackgroundWork.run(
            () -> {
                Critico critico = CriticoDB.obtenerCritico(model.getUser().getIdUsuario());
                String rutaCertificacion = new CloudeClient().subirArchivo(archivo, "application/pdf");
                critico.setCertificacion(rutaCertificacion);
                critico.setEstado(EstadoCertificacion.PENDIENTE);
                CriticoDB.modificarCritico(critico);
                return null;
            },
            v -> JOptionPane.showMessageDialog(chooser,"Se ha enviado el archivo exitosamente", "Operación realizada", JOptionPane.INFORMATION_MESSAGE),
            err -> JOptionPane.showMessageDialog(chooser,"Error al enviar el archivo", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }
}
