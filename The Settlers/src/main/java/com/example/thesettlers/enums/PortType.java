package com.example.thesettlers.enums;

public enum PortType {
    BRICK("brick"),
    LUMBER("lumber"),
    ORE("ore"),
    GRAIN("grain"),
    WOOL("wool"),
    ANY("any");

    public final String label;

    private PortType(String label) {
        this.label = label;
    }

    public static PortType fromString(String label) {
        for (PortType pt : PortType.values()) {
            if (pt.label.equalsIgnoreCase(label)) {
                return pt;
            }
        }
        throw new IllegalArgumentException("No enum found with label: " + label);
    }
}