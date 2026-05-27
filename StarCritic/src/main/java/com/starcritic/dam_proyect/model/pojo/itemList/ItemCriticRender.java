package com.starcritic.dam_proyect.model.pojo.itemList;

import com.starcritic.dam_proyect.view.UIStyle;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Tarjetas de críticas con estilo cinematográfico oscuro.
 * Críticos verificados reciben un fondo dorado oscuro con borde ámbar.
 *
 * @author Jesús Santos Baquero
 */
public class ItemCriticRender extends JLabel implements ListCellRenderer<ItemCritic> {

    private static final Color CRITIC_BG     = new Color(0x2D2209);
    private static final Color CRITIC_BORDER = new Color(0xC07010);
    private static final Color CRITIC_BADGE  = new Color(0xE8A820);

    public ItemCriticRender() {
        setOpaque(true);
        setVerticalAlignment(JLabel.TOP);
        setHorizontalAlignment(JLabel.LEFT);
        setFont(UIStyle.FONT_BODY);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ItemCritic> list, ItemCritic value,
                                                   int index, boolean isSelected, boolean cellHasFocus) {
        String tipoTexto = value.getTipoUsuario() == null ? "ESTANDAR" : value.getTipoUsuario().name();

        StringBuilder html = new StringBuilder(
                "<html><body style='width:440px; padding:8px; font-family:Segoe UI;'>");

        if (value.isEsCritico()) {
            html.append("<div style='font-size:10pt; color:#").append(hex(CRITIC_BADGE))
                    .append("; font-weight:bold; letter-spacing:1px;'>")
                    .append("&#9733;&nbsp;CRÍTICO VERIFICADO&nbsp;&#9733;</div>");
            html.append("<div style='font-size:14pt; font-weight:bold; color:#F2F2F2; padding-top:4px;'>")
                    .append(escape(value.getNombreUsuario()))
                    .append("&nbsp;<span style='font-size:9pt; color:#6B7A90; font-weight:normal;'>(")
                    .append(tipoTexto).append(")</span></div>");
            html.append("<div style='font-size:12pt; font-style:italic; color:#C8D0DC; padding:6px 0;'>")
                    .append("&ldquo;").append(escape(value.getCritica())).append("&rdquo;</div>");
            html.append("<div style='font-size:11pt; font-weight:bold; color:#").append(hex(CRITIC_BADGE))
                    .append(";'>Puntuación: ").append(value.getPuntuacion()).append(" / 100</div>");
        } else {
            html.append("<div style='font-size:12pt; font-weight:bold; color:#F2F2F2;'>")
                    .append(escape(value.getNombreUsuario()))
                    .append("&nbsp;<span style='font-size:9pt; color:#6B7A90; font-weight:normal;'>(")
                    .append(tipoTexto).append(")</span></div>");
            html.append("<div style='font-size:11pt; color:#C8D0DC; padding:4px 0;'>")
                    .append(escape(value.getCritica())).append("</div>");
            html.append("<div style='font-size:10pt; color:#6B7A90;'>Puntuación: <b style='color:#C8D0DC;'>")
                    .append(value.getPuntuacion()).append("</b> / 100</div>");
        }
        html.append("</body></html>");
        setText(html.toString());

        if (isSelected) {
            setBackground(UIStyle.SELECTION);
            setForeground(UIStyle.SELECTION_FG);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(value.isEsCritico() ? CRITIC_BORDER : UIStyle.ACCENT, 2, true),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        } else if (value.isEsCritico()) {
            setBackground(CRITIC_BG);
            setForeground(UIStyle.TEXT);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(CRITIC_BORDER, 2, true),
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
