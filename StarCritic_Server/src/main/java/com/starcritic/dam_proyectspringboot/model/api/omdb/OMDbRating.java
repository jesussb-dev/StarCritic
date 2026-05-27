package com.starcritic.dam_proyectspringboot.model.api.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OMDbRating {
    @JsonProperty("Source") private String source;
    @JsonProperty("Value")  private String value;

    public String getSource() { return source; }
    public String getValue()  { return value; }

    @Override
    public String toString() { return source + ": " + value; }
}
