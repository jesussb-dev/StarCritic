package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.cloudfare.CloudeClient;
import com.starcritic.dam_proyect.data.database.CriticoDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Critico;
import com.starcritic.dam_proyect.model.pojo.bd.EstadoCertificacion;
import com.starcritic.dam_proyect.model.pojo.itemList.ItemContent;
import com.starcritic.dam_proyect.view.PDFViewerDialog;
import com.starcritic.dam_proyect.view.SearchDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Controlador de la vista de revisión de certificaciones de critico. Permite
 * descargar el PDF de la certificación, aceptarla o rechazarla.
 * @author Jesús Santos Baquero
 */
public class RevisionCertificationsController extends BaseController<SearchDialog> {

    public RevisionCertificationsController(SearchDialog view, Model model) {
        super(view, model);
        addAllUsers();
        view.enableRadioButtons(false);
        view.enablePassButton(true);
        view.setTextPassOptions("Rechazar", "Elige una de las opciones", "Aceptar");
        view.enableDeleteButton(false);
        view.setCancelButtonListener(cancelButtonListener());
        view.setSearchListListener(searchListListener());
        view.setAfterButtonListener(afterButtonListener());
        view.setBeforeButtonListener(beforeButtonListener());
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

    private MouseListener searchListListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewCertification();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                viewCertification();
            }
        };
    }

    private ActionListener afterButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = Integer.parseInt(view.getItemListId(row));
                BackgroundWork.run(
                        () -> {
                            Critico critico = CriticoDB.obtenerCritico(id);
                            critico.setEstado(EstadoCertificacion.ACEPTADA);
                            CriticoDB.modificarCritico(critico);
                            return null;
                        },
                        v -> view.deleteListElement(row),
                        err -> JOptionPane.showMessageDialog(view, "Error al aceptar la certificación", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private ActionListener beforeButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = Integer.parseInt(view.getItemListId(row));
                BackgroundWork.run(
                        () -> {
                            Critico critico = CriticoDB.obtenerCritico(id);
                            critico.setCertificacion(null);
                            critico.setEstado(EstadoCertificacion.RECHAZADA);
                            CriticoDB.modificarCritico(critico);
                            return null;
                        },
                        v -> view.deleteListElement(row),
                        err -> JOptionPane.showMessageDialog(view, "Error al rechazar la certificación", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

    private void addAllUsers() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/iconoPDF.png"));
        BackgroundWork.run(
                () -> CriticoDB.getUsuariosConCertificacionesPendientes(),
                usuarios -> {
                    Set<Map.Entry<Integer, Critico>> entries = usuarios.entrySet();
                    for (Iterator<Map.Entry<Integer, Critico>> it = entries.iterator(); it.hasNext();) {
                        Map.Entry<Integer, Critico> entry = it.next();
                        Integer id = entry.getKey();
                        Critico critico = entry.getValue();
                        view.addElementSearch(new ItemContent(critico.getNombreUsuario() + "(" + id + ")", icon, String.valueOf(id)));
                    }
                },
                err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista", "Error", JOptionPane.ERROR_MESSAGE));
    }

    private void viewCertification() {
        int row = view.getSelectedRow();
        if (row == -1) {
            return;
        }
        int id = Integer.parseInt(view.getItemListId(row));
        BackgroundWork.run(
                () -> {
                    String url = CriticoDB.obtenerCritico(id).getCertificacion();
                    String ruta = System.getProperty("user.dir") + "/Certificaciones/";
                    return new CloudeClient().descargarArchivo(url, ruta);
                },
                cert -> {
                    try {
                        PDFViewerDialog pcd = new PDFViewerDialog(view, true);
                        new PDFViewerController(pcd, cert.getCanonicalPath());
                        pcd.setVisible(true);
                        cert.delete();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(view, "Error: no se ha podido visualizar el archivo", "Error de visualización", JOptionPane.ERROR_MESSAGE);
                    }
                },
                err -> JOptionPane.showMessageDialog(view, "Error: no se ha podido descargar el archivo", "Error de descarga", JOptionPane.ERROR_MESSAGE)
        );
    }

}
