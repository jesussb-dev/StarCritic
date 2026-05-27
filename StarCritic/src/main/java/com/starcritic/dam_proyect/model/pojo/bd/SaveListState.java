/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

import com.starcritic.dam_proyect.model.pojo.bd.ListaUsuario;
import java.util.List;

/**
 * Estado precargado del diálogo "Añadir a lista": listas del usuario y si existe
 * alguna lista en la que el contenido todavía no figura.
 *
 * @author Jesús Santos Baquero
 */
public class SaveListState {
    
    private final List<ListaUsuario> listas;
    private final boolean hayListaSin;
    private final int idContent;

    public SaveListState(List<ListaUsuario> listas, boolean hayListaSin, int idContent) {
        this.listas = listas;
        this.hayListaSin = hayListaSin;
        this.idContent = idContent;
    }

    public List<ListaUsuario> getListas() {
        return listas;
    }

    public boolean isHayListaSin() {
        return hayListaSin;
    }

    public int getIdContent() {
        return idContent;
    }
    
}
