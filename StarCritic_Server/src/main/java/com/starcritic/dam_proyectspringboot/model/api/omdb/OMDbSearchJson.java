package com.starcritic.dam_proyectspringboot.model.api.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OMDbSearchJson {
    @JsonProperty("Title")  private String title;
    @JsonProperty("Year")   private String year;
    @JsonProperty("imdbID") private String imdbId;
    @JsonProperty("Type")   private String type;
    @JsonProperty("Poster") private String poster;

    public String getTitle()  { return title; }
    public String getYear()   { return year; }
    public String getImdbId() { return imdbId; }
    public String getType()   { return type; }

    public String getPoster() {
        return (poster == null || poster.equals("N/A")) ? null : poster;
    }

    @Override
    public String toString() { return title + " (" + year + ") [" + imdbId + "]"; }
}
