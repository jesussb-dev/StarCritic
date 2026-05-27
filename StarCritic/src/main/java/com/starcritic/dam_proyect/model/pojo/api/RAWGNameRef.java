/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.api;

/**
 * Referencia genérica id/nombre/slug que devuelve la API RAWG en colecciones
 * como géneros, plataformas o desarrolladores.
 *
 * @author Jesús Santos Baquero
 */
public class RAWGNameRef {

    private int id;
    private String name;
    private String slug;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

}
