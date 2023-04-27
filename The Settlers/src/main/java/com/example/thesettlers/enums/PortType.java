package com.example.thesettlers.enums;

/**
 * The PortType enumeration represents the different types of ports in the Settlers of Catan game.
 * Each value corresponds to a specific type of port, which determines the exchange rate for
 * trading resources with the bank.
 */
public enum PortType {
    /**
     * BRICK represents a brick port, which allows a player to trade brick resources
     * at a favorable exchange rate.
     */
    BRICK("brick"),

    /**
     * LUMBER represents a lumber port, which allows a player to trade lumber resources
     * at a favorable exchange rate.
     */
    LUMBER("lumber"),

    /**
     * ORE represents an ore port, which allows a player to trade ore resources
     * at a favorable exchange rate.
     */
    ORE("ore"),

    /**
     * GRAIN represents a grain port, which allows a player to trade grain resources
     * at a favorable exchange rate.
     */
    GRAIN("grain"),

    /**
     * WOOL represents a wool port, which allows a player to trade wool resources
     * at a favorable exchange rate.
     */
    WOOL("wool"),

    /**
     * ANY represents a generic port, which allows a player to trade any type of resource
     * at a favorable exchange rate.
     */
    ANY("any");

    /**
     * The label of the port type.
     */
    public final String label;

    /**
     * Constructs a PortType instance with the specified label.
     *
     * @param label The label of the port type.
     */
    private PortType(String label) {
        this.label = label;
    }

    /**
     * Returns the PortType enum corresponding to the specified label.
     *
     * @param label The label of the port type.
     * @return The corresponding PortType enum.
     * @throws IllegalArgumentException if no enum is found with the given label.
     */
    public static PortType fromString(String label) {
        for (PortType pt : PortType.values()) {
            if (pt.label.equalsIgnoreCase(label)) {
                return pt;
            }
        }
        throw new IllegalArgumentException("No enum found with label: " + label);
    }
}