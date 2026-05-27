/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Utilidad para renderizar la primera página de un PDF como imagen y poder
 * mostrarla en un {@link JLabel}.
 * @author Jesús Santos Baquero
 */
public class PDFViewer {
    /**
     * Renderizar la primera página de un PDF a 150 DPI y devolverla como icono.
     * @param route la ruta absoluta del fichero PDF.
     * @return un {@link ImageIcon} con la página renderizada, o null si la lectura falla.
     */
    public static ImageIcon getPDF(String route){
        ImageIcon image = null;
        try (PDDocument documento = Loader.loadPDF(new File(route))) {
            PDFRenderer render = new PDFRenderer(documento);
            BufferedImage imagen = render.renderImageWithDPI(0, 150);
            image = new ImageIcon(imagen);
        } catch (IOException ex) {
            System.getLogger(PDFViewer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return image;
    }
}
