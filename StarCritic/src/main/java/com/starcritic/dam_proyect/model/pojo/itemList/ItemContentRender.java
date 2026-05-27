package com.starcritic.dam_proyect.model.pojo.itemList;

import com.starcritic.dam_proyect.view.UIStyle;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

/**
 * Tarjetas de contenido con estilo cinematográfico oscuro.
 * Borde dorado al seleccionar, fondo oscuro alterno entre filas.
 *
 * @author Jesús Santos Baquero
 */
public class ItemContentRender extends JLabel implements ListCellRenderer<ItemContent> {

    private static final Font TITLE_FONT = new Font(UIStyle.FONT_FAMILY, Font.BOLD, 18);
    private static final Border NORMAL_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyle.DIVIDER),
            BorderFactory.createEmptyBorder(8, 12, 8, 12));
    private static final Border SELECTED_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 1, 0, UIStyle.ACCENT),
            BorderFactory.createEmptyBorder(8, 9, 8, 12));

    public ItemContentRender() {
        setOpaque(true);
        setIconTextGap(14);
        setHorizontalTextPosition(JLabel.TRAILING);
        setVerticalTextPosition(JLabel.CENTER);
        setHorizontalAlignment(JLabel.LEFT);
        setVerticalAlignment(JLabel.CENTER);
        setFont(TITLE_FONT);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ItemContent> list, ItemContent value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setText("<html><body style='width:380px; font-family:Segoe UI;'>"
                + "<span style='font-size:14pt; font-weight:bold; color:#F2F2F2;'>"
                + (value.getTexto() == null ? "" : value.getTexto())
                + "</span></body></html>");
        setIcon(value.getIcon());

        if (isSelected) {
            setBackground(UIStyle.SELECTION);
            setForeground(UIStyle.SELECTION_FG);
            setBorder(SELECTED_BORDER);
        } else {
            Color base = (index % 2 == 0) ? UIStyle.BG_CARD : UIStyle.ROW_ALT;
            setBackground(base);
            setForeground(UIStyle.TEXT);
            setBorder(NORMAL_BORDER);
        }
        return this;
    }
}
