/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.starcritic.dam_proyect.view;

import com.starcritic.dam_proyect.model.pojo.bd.TipoContenido;
import java.awt.event.ActionListener;

/**
 * Vista unificada para añadir y modificar contenido LOCAL.
 * Usar setTitleLabelText() y setConfirmButtonText() para adaptar el modo.
 *
 * @author JESUS SB
 */
public class ModifyContentDialog extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ModifyContentDialog.class.getName());

    public ModifyContentDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        applyStyle();
        pack();
        setLocationRelativeTo(parent);
    }

    public ModifyContentDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        applyStyle();
        pack();
        setLocationRelativeTo(parent);
    }

    private void applyStyle() {
        getContentPane().setBackground(UIStyle.BG_PRIMARY);
        backgroundPanel.setBackground(UIStyle.BG_PRIMARY);
        backgroundPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 28, 24, 28));

        titleLabel.setFont(UIStyle.FONT_SUBTITLE);
        titleLabel.setForeground(UIStyle.ACCENT);
        UIStyle.styleFormLabel(nameLabel);
        UIStyle.styleFormLabel(imageLabel);
        UIStyle.styleFormLabel(textAreaLabel);
        UIStyle.styleFormLabel(contentLabel);
        UIStyle.styleField(titleTextField);
        UIStyle.styleMutedLabel(imageStateLabel);
        UIStyle.styleSecondaryButton(imageButton);
        UIStyle.stylePrimaryButton(confirmButton);
        UIStyle.styleDangerButton(cancelButton);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(UIStyle.BORDER, 1, true));
        jScrollPane1.getViewport().setBackground(UIStyle.BG_SUBTLE);
        areaTextPane.setBackground(UIStyle.BG_SUBTLE);
        areaTextPane.setForeground(UIStyle.TEXT);
        areaTextPane.setCaretColor(UIStyle.ACCENT);
        areaTextPane.setFont(UIStyle.FONT_INPUT);
        setTitle("Star Critic — Gestionar contenido");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();
        imageLabel = new javax.swing.JLabel();
        imageButton = new javax.swing.JButton();
        textAreaLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaTextPane = new javax.swing.JTextPane();
        contentLabel = new javax.swing.JLabel();
        contentComboBox = new javax.swing.JComboBox<>();
        cancelButton = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();
        imageStateLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        backgroundPanel.setBackground(new java.awt.Color(255, 255, 255));

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        titleLabel.setText("CONTENIDO");

        nameLabel.setText("Título:");

        titleTextField.setText("");

        imageLabel.setText("Imagen:");

        imageButton.setText("Escoger imagen...");

        textAreaLabel.setText("Sinopsis:");

        jScrollPane1.setViewportView(areaTextPane);

        contentLabel.setText("Tipo de contenido:");

        contentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pelicula", "Serie", "Videojuego"}));

        cancelButton.setText("Cancelar");

        confirmButton.setText("Confirmar");

        imageStateLabel.setText("");

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(titleTextField))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(confirmButton)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton))
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addComponent(imageLabel)
                        .addGap(18, 18, 18)
                        .addComponent(imageStateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(imageButton))
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textAreaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addComponent(contentLabel)
                                .addGap(18, 18, 18)
                                .addComponent(contentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contentLabel)
                    .addComponent(contentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imageLabel)
                    .addComponent(imageButton)
                    .addComponent(imageStateLabel))
                .addGap(18, 18, 18)
                .addComponent(textAreaLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(confirmButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // --- Getters ---

    public String getTitleText() {
        return this.titleTextField.getText();
    }

    public String getTextAreaText() {
        return this.areaTextPane.getText();
    }

    public TipoContenido getSelectionComboBoxValue() {
        return TipoContenido.valueOf(this.contentComboBox.getModel().getSelectedItem().toString().toUpperCase());
    }

    public String getImageStatusLabelText() {
        return this.imageStateLabel.getText();
    }

    // --- Setters para pre-rellenar en modo edición ---

    public void setTitleLabelText(String value) {
        this.titleLabel.setText(value);
    }

    public void setConfirmButtonText(String value) {
        this.confirmButton.setText(value);
    }

    public void setTitleText(String value) {
        this.titleTextField.setText(value);
    }

    public void setSynopsisText(String value) {
        this.areaTextPane.setText(value);
    }

    public void setContentType(TipoContenido tipo) {
        String name = tipo.name();
        String item = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
        this.contentComboBox.setSelectedItem(item);
    }

    public void setImageStatusLabelText(String value) {
        this.imageStateLabel.setText(value);
    }

    public void disableContentComboBox() {
        this.contentComboBox.setEnabled(false);
    }

    // --- Listeners ---

    public void setConfirmButtonListener(ActionListener al) {
        this.confirmButton.addActionListener(al);
    }

    public void setCancelButtonListener(ActionListener al) {
        this.cancelButton.addActionListener(al);
    }

    public void setImageButtonListener(ActionListener al) {
        this.imageButton.addActionListener(al);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane areaTextPane;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> contentComboBox;
    private javax.swing.JLabel contentLabel;
    private javax.swing.JButton confirmButton;
    private javax.swing.JButton imageButton;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel imageStateLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel textAreaLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    // End of variables declaration//GEN-END:variables
}
