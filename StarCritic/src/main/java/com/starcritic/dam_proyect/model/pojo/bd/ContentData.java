/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

/**
 *
 * @author jsanbaq
 */
public class ContentData {
    
    private final String poster;
    private final String title;
    private final String ratingText;
    private final String genre;
    private final String description;
    private final String tags;
    private final boolean userOptionsEnabled;

    public ContentData(String poster, String title, String ratingText, String genre, String description, String tags, boolean userOptionsEnabled) {
        this.poster = poster;
        this.title = title;
        this.ratingText = ratingText;
        this.genre = genre;
        this.description = description;
        this.tags = tags;
        this.userOptionsEnabled = userOptionsEnabled;
    }
    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getRatingText() {
        return ratingText;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    public boolean isUserOptionsEnabled() {
        return userOptionsEnabled;
    }
    
}
