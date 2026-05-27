package com.starcritic.dam_proyect.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Sistema de diseño cinematográfico oscuro para Star Critic.
 * Paleta dorada sobre fondos oscuros — todos los diálogos comparten esta apariencia.
 *
 * @author Jesús Santos Baquero
 */
public final class UIStyle {

    private UIStyle() {}

    // ── Fondos ────────────────────────────────────────────────────────────────
    public static final Color BG_PRIMARY    = new Color(0x13141B);
    public static final Color BG_CARD       = new Color(0x1C1E2A);
    public static final Color BG_SUBTLE     = new Color(0x252836);

    // ── Acento dorado (estrella, cine) ────────────────────────────────────────
    public static final Color ACCENT        = new Color(0xE8A820);
    public static final Color ACCENT_DARK   = new Color(0xC07010);
    public static final Color ACCENT_SOFT   = new Color(0x2D2209);

    // ── Texto ─────────────────────────────────────────────────────────────────
    public static final Color TITLE         = new Color(0xF2F2F2);
    public static final Color TEXT          = new Color(0xC8D0DC);
    public static final Color TEXT_MUTED    = new Color(0x6B7A90);
    public static final Color BORDER        = new Color(0x2D3140);
    public static final Color DIVIDER       = new Color(0x22263A);

    // ── Semánticos ────────────────────────────────────────────────────────────
    public static final Color SUCCESS       = new Color(0x22C55E);
    public static final Color DANGER        = new Color(0xEF4444);

    // ── Listas ────────────────────────────────────────────────────────────────
    public static final Color ROW_ALT       = new Color(0x191B27);
    public static final Color SELECTION     = new Color(0x3D2A08);
    public static final Color SELECTION_FG  = new Color(0xFFD700);

    // ── Tipografía ────────────────────────────────────────────────────────────
    public static final String FONT_FAMILY  = "Segoe UI";
    public static final Font FONT_TITLE     = new Font(FONT_FAMILY, Font.BOLD, 28);
    public static final Font FONT_SUBTITLE  = new Font(FONT_FAMILY, Font.BOLD, 16);
    public static final Font FONT_LABEL     = new Font(FONT_FAMILY, Font.BOLD, 13);
    public static final Font FONT_BODY      = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_INPUT     = new Font(FONT_FAMILY, Font.PLAIN, 14);
    public static final Font FONT_BUTTON    = new Font(FONT_FAMILY, Font.BOLD, 13);

    private static final Color BTN_PRIMARY_FG = new Color(0x1A1000);

    // ── Ventanas ──────────────────────────────────────────────────────────────
    public static void styleWindow(Window w) {
        if (w instanceof JDialog d) {
            d.getContentPane().setBackground(BG_PRIMARY);
        } else if (w instanceof JFrame f) {
            f.getContentPane().setBackground(BG_PRIMARY);
        }
        applyRecursive(w);
    }

    // ── Etiquetas ─────────────────────────────────────────────────────────────
    public static void styleTitle(JLabel label) {
        label.setFont(FONT_TITLE);
        label.setForeground(TITLE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(6, 0, 10, 0));
    }

    public static void styleSubtitle(JLabel label) {
        label.setFont(FONT_SUBTITLE);
        label.setForeground(ACCENT);
    }

    public static void styleFormLabel(JLabel label) {
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT);
    }

    public static void styleBodyLabel(JLabel label) {
        label.setFont(FONT_BODY);
        label.setForeground(TEXT);
    }

    public static void styleMutedLabel(JLabel label) {
        label.setFont(new Font(FONT_FAMILY, Font.ITALIC, 12));
        label.setForeground(TEXT_MUTED);
    }

    // ── Campos de entrada ─────────────────────────────────────────────────────
    public static void styleField(JTextField field) {
        field.setFont(FONT_INPUT);
        field.setBackground(BG_SUBTLE);
        field.setForeground(TEXT);
        field.setCaretColor(ACCENT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        field.putClientProperty("JComponent.roundRect", true);
    }

    public static void styleTextArea(JTextArea area) {
        area.setFont(FONT_INPUT);
        area.setBackground(BG_SUBTLE);
        area.setForeground(TEXT);
        area.setCaretColor(ACCENT);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
    }

    public static void stylePasswordField(JPasswordField field) {
        field.setFont(FONT_INPUT);
        field.setBackground(BG_SUBTLE);
        field.setForeground(TEXT);
        field.setCaretColor(ACCENT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        field.putClientProperty("JComponent.roundRect", true);
    }

    public static void styleSpinner(JSpinner spinner) {
        spinner.setFont(FONT_INPUT);
        spinner.setBackground(BG_SUBTLE);
        spinner.setForeground(TEXT);
        spinner.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        if (spinner.getEditor() instanceof JSpinner.DefaultEditor de) {
            de.getTextField().setBackground(BG_SUBTLE);
            de.getTextField().setForeground(TEXT);
            de.getTextField().setCaretColor(ACCENT);
            de.getTextField().setBorder(new EmptyBorder(4, 8, 4, 8));
        }
    }

    // ── Botones ───────────────────────────────────────────────────────────────
    public static void stylePrimaryButton(JButton button) {
        button.setFont(FONT_BUTTON);
        button.setBackground(ACCENT);
        button.setForeground(BTN_PRIMARY_FG);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 18, 8, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.putClientProperty("JButton.buttonType", "roundRect");
    }

    public static void styleSecondaryButton(JButton button) {
        button.setFont(FONT_BUTTON);
        button.setBackground(BG_CARD);
        button.setForeground(ACCENT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_DARK, 1, true),
                new EmptyBorder(7, 16, 7, 16)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.putClientProperty("JButton.buttonType", "roundRect");
    }

    public static void styleDangerButton(JButton button) {
        button.setFont(FONT_BUTTON);
        button.setBackground(BG_CARD);
        button.setForeground(DANGER);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DANGER, 1, true),
                new EmptyBorder(7, 16, 7, 16)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.putClientProperty("JButton.buttonType", "roundRect");
    }

    public static void styleIconButton(JButton button) {
        button.setBorder(new EmptyBorder(6, 6, 6, 6));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleRadio(JRadioButton radio) {
        radio.setFont(FONT_LABEL);
        radio.setOpaque(false);
        radio.setForeground(TEXT);
        radio.setFocusPainted(false);
        radio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // ── Contenedores ──────────────────────────────────────────────────────────
    public static void styleCard(JPanel panel) {
        panel.setOpaque(true);
        panel.setBackground(BG_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(14, 16, 14, 16)));
    }

    public static void styleScrollPane(JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(true);
        scroll.getViewport().setBackground(BG_CARD);
        scroll.setBackground(BG_CARD);
    }

    public static void styleList(JList<?> list) {
        list.setOpaque(true);
        list.setBackground(BG_CARD);
        list.setForeground(TEXT);
        list.setSelectionBackground(SELECTION);
        list.setSelectionForeground(SELECTION_FG);
        list.setFont(FONT_BODY);
    }

    // ── Utilidades ────────────────────────────────────────────────────────────
    public static Border paddedBorder(int top, int left, int bottom, int right) {
        return new EmptyBorder(top, left, bottom, right);
    }

    public static JPanel gradientHeader(String text) {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                g2.setPaint(new GradientPaint(0, 0, BG_SUBTLE, 0, h, BG_PRIMARY));
                g2.fillRect(0, 0, w, h);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
                g2.setPaint(new GradientPaint(0, h - 2, ACCENT_DARK, w, h - 2, ACCENT));
                g2.fillRect(0, h - 2, w, 2);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14, 18, 16, 18));
        JLabel l = new JLabel(text);
        styleTitle(l);
        header.add(l);
        return header;
    }

    public static Dimension preferredButton(int width) {
        return new Dimension(width, 36);
    }

    public static void applyRecursive(Component root) {
        if (root instanceof JPanel p && p.isOpaque()) {
            Color bg = p.getBackground();
            if (bg != null && isDefaultLightBg(bg)) {
                p.setBackground(BG_PRIMARY);
            }
        }
        if (root instanceof java.awt.Container c) {
            for (Component child : c.getComponents()) {
                applyRecursive(child);
            }
        }
    }

    private static boolean isDefaultLightBg(Color c) {
        return c.getRed() > 200 && c.getGreen() > 200 && c.getBlue() > 200;
    }

    public static void center(Window w) {
        w.setLocationRelativeTo(w.getOwner());
    }

    public static JPanel surface(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(BG_PRIMARY);
        return p;
    }

    public static void installGlobalDefaults() {
        UIManager.put("Panel.background", BG_PRIMARY);
        UIManager.put("OptionPane.background", BG_PRIMARY);
        UIManager.put("Dialog.background", BG_PRIMARY);
        UIManager.put("Component.focusColor", ACCENT);
        UIManager.put("Component.focusedBorderColor", ACCENT);
        UIManager.put("TextComponent.selectionBackground", SELECTION);
        UIManager.put("TextComponent.selectionForeground", SELECTION_FG);
        UIManager.put("List.selectionBackground", SELECTION);
        UIManager.put("List.selectionForeground", SELECTION_FG);
        UIManager.put("List.background", BG_CARD);
        UIManager.put("Table.selectionBackground", SELECTION);
        UIManager.put("Table.selectionForeground", SELECTION_FG);
        UIManager.put("TabbedPane.underlineColor", ACCENT);
        UIManager.put("ScrollBar.thumb", new Color(0x3A3E52));
        UIManager.put("ScrollBar.track", BG_PRIMARY);
        UIManager.put("ScrollBar.hoverThumbColor", new Color(0x4D5270));
        UIManager.put("ScrollBar.pressedThumbColor", ACCENT);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("defaultFont", FONT_BODY);
        UIManager.put("TextField.background", BG_SUBTLE);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.caretForeground", ACCENT);
        UIManager.put("TextArea.background", BG_SUBTLE);
        UIManager.put("TextArea.foreground", TEXT);
        UIManager.put("PasswordField.background", BG_SUBTLE);
        UIManager.put("PasswordField.foreground", TEXT);
        UIManager.put("Spinner.background", BG_SUBTLE);
        UIManager.put("ComboBox.background", BG_CARD);
        UIManager.put("ComboBox.foreground", TEXT);
        UIManager.put("PopupMenu.background", BG_CARD);
        UIManager.put("MenuItem.background", BG_CARD);
        UIManager.put("MenuItem.foreground", TEXT);
        UIManager.put("MenuItem.selectionBackground", ACCENT_SOFT);
        UIManager.put("MenuItem.selectionForeground", ACCENT);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("OptionPane.buttonBackground", BG_CARD);
    }
}
