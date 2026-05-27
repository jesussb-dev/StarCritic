package com.starcritic.dam_proyectspringboot.model.api.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OMDbListSearch {
    @JsonProperty("Search")       private List<OMDbSearchJson> contenidos;
    @JsonProperty("totalResults") private String totalResults;
    @JsonProperty("Response")     private String response;
    @JsonProperty("Error")        private String error;

    public List<OMDbSearchJson> getContenidos() { return contenidos; }
    public String getTotalResults()             { return totalResults; }
    public String getResponse()                 { return response; }
    public String getError()                    { return error; }

    public int getTotalResultsAsInt() {
        if (totalResults == null) return 0;
        try { return Integer.parseInt(totalResults.trim()); }
        catch (NumberFormatException e) { return 0; }
    }
}
