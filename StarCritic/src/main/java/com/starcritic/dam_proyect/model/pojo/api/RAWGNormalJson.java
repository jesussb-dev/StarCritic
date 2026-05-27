/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author jsanbaq
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
