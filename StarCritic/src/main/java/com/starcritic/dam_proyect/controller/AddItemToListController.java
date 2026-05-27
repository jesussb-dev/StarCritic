package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.ListaContenidoDB;
import com.starcritic.dam_proyect.data.database.ListaUsuarioDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.model.pojo.bd.ListaUsuario;
import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import com.starcritic.dam_proyect.view.CompleteItemDialog;
import com.starcritic.dam_proyect.view.CriticsDialog;
import com.starcritic.dam_proyect.view.ListsUserDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AddItemToListController extends BaseController<ListsUserDialog> {

    private  CompleteItemDialog parentView;
    private  int idContenido;
    private  TipoContenido type;

    public AddItemToListController(ListsUserDialog view, CompleteItemDialog parentView, Model model, int idContenido, TipoContenido type) {
        super(view, model);
        this.parentView = parentView;
        this.idContenido = idContenido;
        this.type = type;
        view.enableListButtons(false);
        addListUser();
        view.setCancelButtonListener(cancelButtonListener());
        view.setSearchButtonListener(searchButtonListener());
        view.setListUserListener(listUserListener());
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

    private ActionListener searchButtonListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.clearList();
                addListUser();
            }
        };
        return al;
    }

    private MouseListener listUserListener() {
        return new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { 
        if (view.getSelectedList() == -1) return;
        String nombreLista = view.getSelectedListValue();
        if (nombreLista == null) return;
        BackgroundWork.run(
            () -> {
                ListaContenidoDB.anadirContenidoALista(model.getUser().getIdUsuario(), nombreLista, idContenido);
                return null;
            },
            v -> {
                JOptionPane.showMessageDialog(view, "Se ha integrado en la lista","Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                view.dispose();
                if ("Vistos y Jugados".equals(nombreLista.trim())) {
                    int confirm = JOptionPane.showConfirmDialog(parentView,"Si ya has visto este contenido, ¿te gustaría hacer una reseña?","Hacer reseña", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        CriticsDialog cd = new CriticsDialog(parentView, true);
                        new CriticsController(cd, model, idContenido, type);
                        cd.setVisible(true);
                    }
                }
            },
            err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista","Error", JOptionPane.ERROR_MESSAGE));
            }
        };
    }



    private void addListUser() {
        String filtro = view.getSearchText() != null ? view.getSearchText().toLowerCase().trim() : "";
        BackgroundWork.run(
            () -> {
                List<ListaUsuario> listas = ListaUsuarioDB.obtenerListasUsuario(model.getUser().getIdUsuario());
                if (listas == null) return new ArrayList<String>();
                List<String> result = new ArrayList<>();
                for (ListaUsuario lista : listas) {
                    if (filtro.isEmpty() || lista.getNombreLista().toLowerCase().contains(filtro)) {
                        if (!ListaContenidoDB.existeContenidoEnLista(model.getUser().getIdUsuario(),lista.getNombreLista(), idContenido)) {
                            result.add(lista.getNombreLista());
                        }
                    }
                }
                return result;
            },
            nombres -> nombres.forEach(view::addItemList),
            err -> JOptionPane.showMessageDialog(view, "Error al añadir el contenido a la lista","Error", JOptionPane.ERROR_MESSAGE));
    }
}
