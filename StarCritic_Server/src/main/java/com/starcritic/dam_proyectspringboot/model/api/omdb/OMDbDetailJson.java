package com.starcritic.dam_proyectspringboot.model.api.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @author Jesús Santos Baquero
 */
public class OMDbDetailJson {
    @JsonProperty("Title")        private String title;
    @JsonProperty("Year")         private String year;
    @JsonProperty("Rated")        private String rated;
    @JsonProperty("Released")     private String released;
    @JsonProperty("Runtime")      private String runtime;
    @JsonProperty("Genre")        private String genre;
    @JsonProperty("Director")     private String director;
    @JsonProperty("Writer")       private String writer;
    @JsonProperty("Actors")       private String actors;
    @JsonProperty("Plot")         private String plot;
    @JsonProperty("Language")     private String language;
    @JsonProperty("Country")      private String country;
    @JsonProperty("Awards")       private String awards;
    @JsonProperty("Poster")       private String poster;
    @JsonProperty("Ratings")      private List<OMDbRating> ratings;
    @JsonProperty("Metascore")    private String metascore;
    @JsonProperty("imdbRating")   private String imdbRating;
    @JsonProperty("imdbVotes")    private String imdbVotes;
    @JsonProperty("imdbID")       private String imdbId;
    @JsonProperty("Type")         private String type;
    @JsonProperty("totalSeasons") private String totalSeasons;
    @JsonProperty("Response")     private String response;
    @JsonProperty("Error")        private String error;

    private static String naToNull(String s) {
        return (s == null || s.equals("N/A")) ? null : s;
    }

    public int getTotalSeasons() {
        String s = naToNull(totalSeasons);
        if (s == null) return 0;
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { return 0; }
    }

    public boolean isSeries() { return "series".equalsIgnoreCase(type); }
    public boolean isMovie()  { return "movie".equalsIgnoreCase(type); }

    public String getTitle()      { return naToNull(title); }
    public String getYear()       { return naToNull(year); }
    public String getRated()      { return naToNull(rated); }
    public String getReleased()   { return naToNull(released); }
    public String getRuntime()    { return naToNull(runtime); }
    public String getGenre()      { return naToNull(genre); }
    public String getDirector()   { return naToNull(director); }
    public String getWriter()     { return naToNull(writer); }
    public String getActors()     { return naToNull(actors); }
    public String getPlot()       { return naToNull(plot); }
    public String getLanguage()   { return naToNull(language); }
    public String getCountry()    { return naToNull(country); }
    public String getAwards()     { return naToNull(awards); }
    public String getPoster()     { return naToNull(poster); }
    public String getMetascore()  { return naToNull(metascore); }
    public String getImdbRating() { return naToNull(imdbRating); }
    public String getImdbVotes()  { return naToNull(imdbVotes); }
    public String getImdbId()     { return imdbId; }
    public String getType()       { return type; }
    public String getResponse()   { return response; }
    public String getError()      { return error; }
    public List<OMDbRating> getRatings() { return ratings; }
}
