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
public class OMDbDetailJson {
    @SerializedName("Title")        private String title;
    @SerializedName("Year")         private String year;
    @SerializedName("Rated")        private String rated;
    @SerializedName("Released")     private String released;
    @SerializedName("Runtime")      private String runtime;
    @SerializedName("Genre")        private String genre;
    @SerializedName("Director")     private String director;
    @SerializedName("Writer")       private String writer;
    @SerializedName("Actors")       private String actors;
    @SerializedName("Plot")         private String plot;
    @SerializedName("Language")     private String language;
    @SerializedName("Country")      private String country;
    @SerializedName("Awards")       private String awards;
    @SerializedName("Poster")       private String poster;
    @SerializedName("Ratings")      private List<OMDbRating> ratings;
    @SerializedName("Metascore")    private String metascore;
    @SerializedName("imdbRating")   private String imdbRating;
    @SerializedName("imdbVotes")    private String imdbVotes;
    @SerializedName("imdbID")       private String imdbId;
    @SerializedName("Type")         private String type;
    @SerializedName("totalSeasons") private String totalSeasons;   // solo en series
    @SerializedName("Response")     private String response;
    @SerializedName("Error")        private String error;
  
    /** Solo aplica a series. Devuelve 0 si es película o no hay dato. */
    public int getTotalSeasons() {
        String s = naToNull(totalSeasons);
        if (s == null){ return 0;}
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { return 0; }
    }

    public boolean isSeries() { 
        return "series".equalsIgnoreCase(type); 
    }
    public boolean isMovie()  { 
        return "movie".equalsIgnoreCase(type);  
    }
 
    private static String naToNull(String s) {
        if (s == null){ return null;}
        if (s.equals("N/A")){ return null;}
    return s;
    }
    
    
    public String getTitle(){ 
        return naToNull(title); 
    } 
    public String getYear(){ 
        return naToNull(year); 
    }
    public String getRated(){ 
        return naToNull(rated); 
    }
    public String getReleased(){ 
        return naToNull(released); 
    }
    public String getRuntime(){ 
        return naToNull(runtime); 
    }
    public String getGenre(){ 
        return naToNull(genre); 
    }
    public String getDirector(){ 
        return naToNull(director); 
    }
    public String getWriter(){ 
        return naToNull(writer); 
    }
    public String getActors(){ 
        return naToNull(actors); 
    }
    public String getPlot(){ 
        return naToNull(plot); 
    }
    public String getLanguage(){ 
        return naToNull(language); 
    }
    public String getCountry(){ 
        return naToNull(country); 
    }
    public String getAwards(){ 
        return naToNull(awards); 
    }
    public String getPoster(){ 
        return naToNull(poster); 
    }
    public String getMetascore(){ 
        return naToNull(metascore); 
    }
    public String getImdbRating(){ 
        return naToNull(imdbRating); 
    }
    public String getImdbVotes(){ 
        return naToNull(imdbVotes); 
    }
    public String getImdbId(){ 
        return imdbId; 
    }
    public String getType(){ 
        return type; 
    }


}
