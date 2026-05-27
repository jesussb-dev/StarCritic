package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.MensajeDB;
import com.starcritic.dam_proyect.data.database.UsuarioDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.Mensaje;
import com.starcritic.dam_proyect.model.pojo.bd.UsuarioRegistrado;
import com.starcritic.dam_proyect.model.pojo.itemList.ItemMessage;
import com.starcritic.dam_proyect.view.MessagesDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Controlador del diálogo de bandeja de entrada de mensajes. Carga los mensajes
 * recibidos del usuario actual y marca como leidos los que se abren.
 * @author Jesús Santos Baquero
 */
public class MessageRecieveController extends BaseController<MessagesDialog> {

    public MessageRecieveController(MessagesDialog view, Model model) {
        super(view, model);
        view.setCancelButtonListener(cancelButtonListener());
        view.setMessageListListener(messageListListener());
        loadMessages();
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

    private MouseListener messageListListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getSelectedMessage();
                if (row != -1) {
                    ItemMessage message = view.getMessageAt(row);
                    JOptionPane.showMessageDialog(view, message.getContenido(), message.getAsunto(), JOptionPane.INFORMATION_MESSAGE);
                    if (!message.isLeido()) {
                        marcarComoLeido(message);
                    }
                }
            }
        };
    }

    private void loadMessages() {
        view.clearMessages();
        int idUsuario = model.getUser().getIdUsuario();
        BackgroundWork.run(
                () -> {
                    List<Mensaje> mensajes = MensajeDB.obtenerTodosLosMensajesParaUsuario(idUsuario);
                    Map<Integer, String> nombres = new HashMap<>();
                    List<ItemMessage> items = new ArrayList<>();
                    for (Mensaje m : mensajes) {
                        String nombre = nombres.get(m.getIdRemitente());
                        if (nombre == null) {
                            UsuarioRegistrado u = UsuarioDB.obtenerUsuario(m.getIdRemitente());
                            nombre = u == null ? null : u.getNombreUsuario();
                            nombres.put(m.getIdRemitente(), nombre);
                        }
                        items.add(new ItemMessage(m.getIdMensaje(), m.getIdRemitente(), nombre, m.getAsunto(), m.getContenido(), m.getFechaEnvio(), m.isLeido()));
                    }
                    return items;
                },
                items -> {
                    for (ItemMessage item : items) {
                        view.addItemList(item);
                    }
                },
                err -> JOptionPane.showMessageDialog(view, "Error al cargar los mensajes", "Error", JOptionPane.ERROR_MESSAGE));
    }

    private void marcarComoLeido(ItemMessage item) {
        LocalDateTime ahora = LocalDateTime.now();
        Mensaje mensaje = new Mensaje(item.getIdRemitente(), model.getUser().getIdUsuario(),
                item.getAsunto(), item.getContenido(), item.getFechaEnvio(), true);
        mensaje.setIdMensaje(item.getIdMensaje());
        mensaje.setFechaLectura(ahora);
        BackgroundWork.run(
                () -> MensajeDB.modificarMensaje(mensaje),
                ok -> {
                    if (Boolean.TRUE.equals(ok)) {
                        item.setLeido(true);
                        loadMessages();
                    }
                },
                err -> JOptionPane.showMessageDialog(view, "Error al marcar el mensaje como leído", "Error", JOptionPane.ERROR_MESSAGE));

    }
}
