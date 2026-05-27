package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.MensajeDB;
import com.starcritic.dam_proyect.data.database.UsuarioDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Mensaje;
import com.starcritic.dam_proyect.view.MessageDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

public class MessageController extends BaseController<MessageDialog> {

    private final int idDestinatario;

    public MessageController(MessageDialog view, Model model, int idDestinatario) {
        super(view, model);
        this.idDestinatario = idDestinatario;
        view.setCancelButtonListener(cancelButtonListener());
        view.setSendButtonListener(sendButtonListener());
        BackgroundWork.runVoid(
                () -> {
                    String nombre = UsuarioDB.obtenerUsuario(idDestinatario).getNombreUsuario();
                    view.setUserLaber(nombre);
                },
                err -> {
                    JOptionPane.showMessageDialog(view, "Error: no se ha seleccionado el usuario correctamente", "Error", JOptionPane.ERROR_MESSAGE);
                }
        );
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

    private ActionListener sendButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String asunto = view.getAsunto();
                String texto = view.getContent();
                if (asunto == null || asunto.isBlank() || texto == null || texto.isBlank()) {
                    JOptionPane.showMessageDialog(view, "Error: alguno de los campos está vacío", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Mensaje mensaje = new Mensaje(
                        model.getUser().getIdUsuario(), idDestinatario, asunto, texto, LocalDateTime.now(), false);
                BackgroundWork.run(
                        () -> {
                            MensajeDB.insertarMensaje(mensaje);
                            return null;
                        },
                        v -> {
                            JOptionPane.showMessageDialog(view, "Se ha enviado correctamente la advertencia", "Mensaje enviado", JOptionPane.INFORMATION_MESSAGE); 
                            view.dispose();
                        },
                        err -> JOptionPane.showMessageDialog(view, "Error al enviar el mensaje", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        };
        return al;
    }

}
