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
public class RAWGListNormal {

    @SerializedName("results")
    private List<RAWGNormalJson> juegos;

    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;

    public List<RAWGNormalJson> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<RAWGNormalJson> juegos) {
        this.juegos = juegos;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public int getTotalResultsAsInt() {
        return count;
    }
}
