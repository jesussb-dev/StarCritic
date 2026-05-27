/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.itemList;

import com.starcritic.dam_proyect.model.pojo.bd.Origen;
import com.starcritic.dam_proyect.util.ImageUtils;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Item de la lista de contenidos listo para pintar con
 * {@link ItemContentRender}. Encapsula texto, poster ya escalado, id y
 * origen para que la vista no tenga que cargar imágenes.
 *
 * @author Jesús Santos Baquero
 */
public class ItemContent {

    private String texto;
    private Icon icon;
    private String id;
    private Origen origen;

    public ItemContent(String texto, Icon icon, String id, Origen origen) {
        this.texto = texto;
        this.icon = convertIcon(icon);
        this.id = id;
        this.origen = origen;
    }
    public ItemContent(String texto, Icon icon, String id) {
        this.texto = texto;
        this.icon = convertIcon(icon);
        this.id = id;

    }
    private static final int ICON_H = 70;

    private Icon convertIcon(Icon icon) {
        ImageIcon iconImage = (ImageIcon) icon;
        Image img = iconImage.getImage();
        // Force full load so dimensions are available for remote/async images
        MediaTracker tracker = new MediaTracker(new JLabel());
        tracker.addImage(img, 0);
        try { tracker.waitForAll(); } catch (InterruptedException ignored) {}
        int origW = img.getWidth(null);
        int origH = img.getHeight(null);
        int targetW = (origH > 0) ? (origW * ICON_H / origH) : ICON_H;
        return new ImageIcon(ImageUtils.scale(img, targetW, ICON_H));
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

}
