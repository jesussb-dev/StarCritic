/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.api;

/**
 *
 * @author jsanbaq
 */
public class RAWGPlatformWrapper {
    private RAWGNameRef platform;
 
    public RAWGNameRef getPlatform() { return platform; }
 
    public String getPlatformName() {
        if (platform == null){return null;}
        return platform.getName();
    }

}
