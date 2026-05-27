package com.starcritic.dam_proyectspringboot.model.api.rawg;

public class RAWGPlatformWrapper {
    private RAWGNameRef platform;

    public RAWGNameRef getPlatform() { return platform; }

    public String getPlatformName() {
        return platform != null ? platform.getName() : null;
    }
}
