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
 *
 * @author jsb
 */
public class PDFViewer {
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
