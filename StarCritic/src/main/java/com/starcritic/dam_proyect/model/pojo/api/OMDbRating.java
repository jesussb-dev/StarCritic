/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.api;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author jsanbaq
 */
public class OMDbRating {
    @SerializedName("Source") private String source;
    @SerializedName("Value")  private String value;
 
    public String getSource() { 
        return source; 
    }
    public String getValue()  { 
        return value;  
    }
 
    @Override
    public String toString() {
        return source + ": " + value;
    }
 
}
