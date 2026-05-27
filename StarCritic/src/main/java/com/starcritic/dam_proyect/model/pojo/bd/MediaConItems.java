/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.model.pojo.bd;

import com.starcritic.dam_proyect.model.pojo.itemList.ItemCritic;
import java.util.List;

/**
 * Wrapper que agrupa una lista de críticas a renderizar junto con su media
 * de puntuación calculada.
 *
 * @author Jesús Santos Baquero
 */
public class MediaConItems {
    private List<ItemCritic> items;
    private double media;

    public MediaConItems(List<ItemCritic> items, double media) {
        this.items = items;
        this.media = media;
    }

    public List<ItemCritic> getItems() {
        return items;
    }

    public void setItems(List<ItemCritic> items) {
        this.items = items;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }
    
}
