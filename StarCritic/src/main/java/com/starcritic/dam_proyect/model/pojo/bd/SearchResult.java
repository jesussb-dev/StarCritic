package com.starcritic.dam_proyect.model.pojo.bd;

import com.starcritic.dam_proyect.model.pojo.itemList.ItemContent;
import java.util.List;

/**
 * Resultado paginado de una búsqueda: la lista de elementos a mostrar y el
 * número total de páginas disponibles.
 *
 * @author Jesús Santos Baquero
 */
public class SearchResult {

    private List<ItemContent> items;
    private int totalPages;

    public SearchResult(List<ItemContent> items, int totalPages) {
        this.items = items;
        this.totalPages = totalPages;
    }

    public List<ItemContent> getItems() {
        return items;
    }

    public void setItems(List<ItemContent> items) {
        this.items = items;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
