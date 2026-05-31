/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.starcritic.dam_proyect.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import org.jfree.chart.ChartPanel;

/**
 * Diálogo de estadísticas globales del catálogo. Pinta los gráficos
 * generados con JFreeChart.
 *
 * @author Jesús Santos Baquero
 */
public class StatsDialog extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StatsDialog.class.getName());

    public StatsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fixLayout();
    }

    public StatsDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fixLayout();
    }

    private void fixLayout() {
        getContentPane().removeAll();
        getContentPane().setBackground(UIStyle.BG_PRIMARY);
        getContentPane().setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Estadísticas");
        UIStyle.styleTitle(titleLabel);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(18, 28, 10, 28));
        titleLabel.setForeground(UIStyle.ACCENT);
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        styleTabPane();
        getContentPane().add(graphTabbedPane, BorderLayout.CENTER);

        setTitle("Star Critic — Estadísticas");
        setPreferredSize(new Dimension(920, 640));
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void styleTabPane() {
        graphTabbedPane.setOpaque(true);
        graphTabbedPane.setBackground(UIStyle.BG_PRIMARY);
        graphTabbedPane.setForeground(UIStyle.TEXT);
        graphTabbedPane.setFont(UIStyle.FONT_LABEL);
        graphTabbedPane.setBorder(BorderFactory.createLineBorder(UIStyle.BORDER, 1));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        graphTabbedPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 573, Short.MAX_VALUE)
                .addComponent(graphTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(graphTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 317, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void initGraph(ChartPanel panel, String title) {
        panel.setBackground(UIStyle.BG_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        graphTabbedPane.add(panel, title);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane graphTabbedPane;
    // End of variables declaration//GEN-END:variables
}
