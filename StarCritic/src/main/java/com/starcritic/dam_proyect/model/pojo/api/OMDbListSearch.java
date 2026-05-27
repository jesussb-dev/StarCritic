/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Respuesta de la búsqueda paginada de OMDb: lista de coincidencias y
 * número total de resultados disponibles.
 *
 * @author Jesús Santos Baquero
 */
public class OMDbListSearch {
    @SerializedName("Search")
    private List<OMDbSearchJson> contenidos;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("Response")
    private String response;

    @SerializedName("Error")
    private String error;

    public List<OMDbSearchJson> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<OMDbSearchJson> contenidos) {
        this.contenidos = contenidos;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getTotalResultsAsInt() {
        if (totalResults == null) return 0;
        try {
            return Integer.parseInt(totalResults.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
