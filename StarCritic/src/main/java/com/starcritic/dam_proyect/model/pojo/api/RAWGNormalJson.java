/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Item de los resultados de RAWG con los datos básicos de un videojuego
 * (id, slug, nombre, fecha, imagen, valoración, géneros y plataformas).
 *
 * @author Jesús Santos Baquero
 */
public class RAWGNormalJson {
    
    private int id;
    private String slug;
    private String name;
    private String released;
    @SerializedName("background_image")
    private String backgroundImage;
    private double rating;
    private int metacritic;
    private int playtime;
    private String description;
    @SerializedName("description_raw")
    private String descriptionRaw;

    private List<RAWGNameRef> genres;
    private List<RAWGPlatformWrapper> platforms;
    private List<RAWGNameRef> tags;

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getReleased() {
        return released;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public double getRating() {
        return rating;
    }

    public int getMetacritic() {
        return metacritic;
    }

    public int getPlaytime() {
        return playtime;
    }

    /** Sinopsis del juego en formato HTML (solo disponible en el detalle). */
    public String getDescription() {
        return description;
    }

    /** Sinopsis del juego en texto plano (solo disponible en el detalle). */
    public String getDescriptionRaw() {
        return descriptionRaw;
    }

    public List getGenres() {
        return genres;
    }

    public List getPlatforms() {
        return platforms;
    }

    public List getTags() {
        return tags;
    }
 

 

}
