package com.example.thesettlers.enums;

public enum ResourceType {
    BRICK("brick"),
    LUMBER("lumber"),
    ORE("ore"),
    GRAIN("grain"),
    WOOL("wool");

    public final String label;

    private ResourceType(String label) {
        this.label = label;
    }

    public static ResourceType getByIndex(int index) {
        return ResourceType.values()[index];
    }
}
