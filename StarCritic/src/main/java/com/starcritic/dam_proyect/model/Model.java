/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model;

import com.starcritic.dam_proyect.data.api.service.OMDbService;
import com.starcritic.dam_proyect.data.api.service.RAWGService;
import com.starcritic.dam_proyect.model.pojo.bd.UsuarioRegistrado;

/**
 * Modelo compartido por toda la aplicación. Mantiene la sesión del usuario
 * y las fachadas hacia los servicios externos OMDb y RAWG (que en esta
 * versión cliente delegan en el backend StarCritic_Server).
 *
 * @author Jesús Santos Baquero
 */
public class Model {
    private UsuarioRegistrado user;
    private OMDbService OMDb;
    private RAWGService RAWG;

    public Model() {
        this.user = null;
        this.OMDb = new OMDbService();
        this.RAWG = new RAWGService();
    }

    public UsuarioRegistrado getUser() {
        return user;
    }

    public void setUser(UsuarioRegistrado user) {
        this.user = user;
    }

    public OMDbService getOMDb() {
        return OMDb;
    }

    public void setOMDb(OMDbService OMDb) {
        this.OMDb = OMDb;
    }

    public RAWGService getRAWG() {
        return RAWG;
    }

    public void setRAWG(RAWGService RAWG) {
        this.RAWG = RAWG;
    }

}
