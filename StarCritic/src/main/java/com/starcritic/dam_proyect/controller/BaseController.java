package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.model.Model;

/**
 * Clase base para todos los controladores MVC del proyecto. Centraliza los
 * campos comunes model y view para evitar duplicación.
 * @param <V> tipo de la vista que gestiona este controlador.
 * @author Jesús Santos Baquero
 */
public abstract class BaseController<V> {

    protected final Model model;
    protected final V view;

    protected BaseController(V view, Model model) {
        this.view = view;
        this.model = model;
    }
}
