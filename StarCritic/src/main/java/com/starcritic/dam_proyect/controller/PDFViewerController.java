/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.PDFViewer;
import com.starcritic.dam_proyect.view.PDFViewerDialog;

/**
 *
 * @author jsb
 */
public class PDFViewerController {
    private PDFViewerDialog view;
    private String route;

    public PDFViewerController(PDFViewerDialog view, String route) {
        this.view = view;
        this.route = route;
        this.initComponents();
        
    }
    
    private void initComponents(){
        view.setPDFLabel(PDFViewer.getPDF(route));
        this.view.pack();
    }
    public PDFViewerDialog getView() {
        return view;
    }

    public void setView(PDFViewerDialog view) {
        this.view = view;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
    
}
