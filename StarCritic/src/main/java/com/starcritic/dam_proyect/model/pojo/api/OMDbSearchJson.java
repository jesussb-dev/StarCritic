/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.api;

import com.google.gson.annotations.SerializedName;

/**
 * Item de los resultados de búsqueda de OMDb (título, año, imdbID, tipo, póster).
 *
 * @author Jesús Santos Baquero
 */
public class OMDbSearchJson {
    
    @SerializedName("Title")  private String title;
    @SerializedName("Year")   private String year;
    @SerializedName("imdbID") private String imdbId;
    @SerializedName("Type")   private String type;     // "movie" o "series"
    @SerializedName("Poster") private String poster;   // URL o "N/A"

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return (poster == null || poster.equals("N/A")) ? null : poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
 

 
    @Override
    public String toString() {
        return title + " (" + year + ") [" + imdbId + "]";
    }

}
