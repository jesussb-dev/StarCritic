package com.starcritic.dam_proyectspringboot.model.api.rawg;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @author Jesús Santos Baquero
 */
public class RAWGNormalJson {
    private int id;
    private String slug;
    private String name;
    private String released;
    @JsonProperty("background_image")
    private String backgroundImage;
    private double rating;
    private Integer metacritic;
    private Integer playtime;
    private String description;
    @JsonProperty("description_raw")
    private String descriptionRaw;
    private List<RAWGNameRef> genres;
    private List<RAWGPlatformWrapper> platforms;
    private List<RAWGNameRef> tags;

    public int getId()                          { return id; }
    public String getSlug()                     { return slug; }
    public String getName()                     { return name; }
    public String getReleased()                 { return released; }
    public String getBackgroundImage()          { return backgroundImage; }
    public double getRating()                   { return rating; }
    public Integer getMetacritic()              { return metacritic; }
    public Integer getPlaytime()                { return playtime; }
    public String getDescription()              { return description; }
    public String getDescriptionRaw()           { return descriptionRaw; }
    public List<RAWGNameRef> getGenres()        { return genres; }
    public List<RAWGPlatformWrapper> getPlatforms() { return platforms; }
    public List<RAWGNameRef> getTags()          { return tags; }
}
