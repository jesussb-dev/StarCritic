package com.starcritic.dam_proyect.model.pojo.bd.listas;

import com.starcritic.dam_proyect.model.pojo.bd.Critico;
import java.util.List;

/**
 * Envoltorio de una lista de {@link Critico} devuelta por la API.
 *
 * @author Jesús Santos Baquero
 */
public class DetallesCritico {

    private List<Critico> criticos;

    public void setCriticos(List<Critico> criticos) {
        this.criticos = criticos;
    }

    public List<Critico> getCriticos() {
        return criticos;
    }

    @Override
    public String toString() {
        return "DetallesCritico{" +
                "criticos=" + criticos +
                '}';
    }
}
