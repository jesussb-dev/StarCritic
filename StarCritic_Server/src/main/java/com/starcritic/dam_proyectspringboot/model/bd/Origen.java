package com.starcritic.dam_proyectspringboot.model.bd;

public enum Origen {
    OMDB, RAWG, LOCAL;

    public boolean esLocal() {
        return this == LOCAL;
    }

    public boolean esExterno() {
        return this != LOCAL;
    }
}
