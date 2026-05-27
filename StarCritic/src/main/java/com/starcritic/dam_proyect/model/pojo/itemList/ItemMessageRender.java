package com.starcritic.dam_proyect.model.pojo.itemList;

import com.starcritic.dam_proyect.view.UIStyle;
import java.awt.Color;
import java.awt.Component;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Tarjetas de mensajes al estilo cinematográfico oscuro.
 * Los mensajes sin leer reciben fondo dorado oscuro con borde ámbar,
 * espejo del tratamiento "crítico verificado" en {@link ItemCriticRender}.
 *
 * @author Jesús Santos Baquero
 */
public class ItemMessageRender extends JLabel implements ListCellRenderer<ItemMessage> {

    private static final Color UNREAD_BG     = new Color(0x2D2209);
    private static final Color UNREAD_BORDER = new Color(0xC07010);
    private static final Color UNREAD_BADGE  = new Color(0xE8A820);

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ItemMessageRender() {
        setOpaque(true);
        setVerticalAlignment(JLabel.TOP);
        setHorizontalAlignment(JLabel.LEFT);
        setFont(UIStyle.FONT_BODY);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ItemMessage> list, ItemMessage value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        String fecha = value.getFechaEnvio() == null ? "" : value.getFechaEnvio().format(DATE_FMT);
        String remitente = value.getNombreRemitente() == null ? "Usuario #" + value.getIdRemitente()
                                                              : value.getNombreRemitente();

        StringBuilder html = new StringBuilder(
                "<html><body style='width:440px; padding:8px; font-family:Segoe UI;'>");

        if (!value.isLeido()) {
            html.append("<div style='font-size:10pt; color:#").append(hex(UNREAD_BADGE))
                    .append("; font-weight:bold; letter-spacing:1px;'>")
                    .append("&#9993;&nbsp;NUEVO MENSAJE&nbsp;&#9993;</div>");
            html.append("<div style='font-size:14pt; font-weight:bold; color:#F2F2F2; padding-top:4px;'>")
                    .append(escape(value.getAsunto()))
                    .append("&nbsp;<span style='font-size:9pt; color:#6B7A90; font-weight:normal;'>(de ")
                    .append(escape(remitente)).append(")</span></div>");
            html.append("<div style='font-size:12pt; font-style:italic; color:#C8D0DC; padding:6px 0;'>")
                    .append("&ldquo;").append(escape(value.getContenido())).append("&rdquo;</div>");
            html.append("<div style='font-size:10pt; font-weight:bold; color:#").append(hex(UNREAD_BADGE))
                    .append(";'>").append(fecha).append("</div>");
        } else {
            html.append("<div style='font-size:12pt; font-weight:bold; color:#F2F2F2;'>")
                    .append(escape(value.getAsunto()))
                    .append("&nbsp;<span style='font-size:9pt; color:#6B7A90; font-weight:normal;'>(de ")
                    .append(escape(remitente)).append(")</span></div>");
            html.append("<div style='font-size:11pt; color:#C8D0DC; padding:4px 0;'>")
                    .append(escape(value.getContenido())).append("</div>");
            html.append("<div style='font-size:10pt; color:#6B7A90;'>").append(fecha).append("</div>");
        }
        html.append("</body></html>");
        setText(html.toString());

        if (isSelected) {
            setBackground(UIStyle.SELECTION);
            setForeground(UIStyle.SELECTION_FG);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(value.isLeido() ? UIStyle.ACCENT : UNREAD_BORDER, 2, true),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        } else if (!value.isLeido()) {
            setBackground(UNREAD_BG);
            setForeground(UIStyle.TEXT);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UNREAD_BORDER, 2, true),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        } else {
            setBackground(index % 2 == 0 ? UIStyle.BG_CARD : UIStyle.ROW_ALT);
            setForeground(UIStyle.TEXT);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyle.DIVIDER),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        }
        return this;
    }

    private static String hex(Color c) {
        return String.format("%06x", c.getRGB() & 0xFFFFFF);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
