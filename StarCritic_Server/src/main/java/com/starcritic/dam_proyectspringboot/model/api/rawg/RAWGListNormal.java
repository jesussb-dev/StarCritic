package com.starcritic.dam_proyectspringboot.model.api.rawg;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @author Jesús Santos Baquero
 */
public class RAWGListNormal {
    @JsonProperty("results") private List<RAWGNormalJson> juegos;
    @JsonProperty("count")   private int count;
    @JsonProperty("next")    private String next;
    @JsonProperty("previous")private String previous;

    public List<RAWGNormalJson> getJuegos() { return juegos; }
    public int getCount()                   { return count; }
    public String getNext()                 { return next; }
    public String getPrevious()             { return previous; }
    public int getTotalResultsAsInt()       { return count; }
}
