package com.example.thesettlers.enums;

/**
 * An enumeration representing the different types of resources
 * that can be used in the game The Settlers.
 */
public enum ResourceType {

    /**
     * Represents the brick resource.
     */
    BRICK("brick"),

    /**
     * Represents the lumber resource.
     */
    LUMBER("lumber"),

    /**
     * Represents the ore resource.
     */
    ORE("ore"),

    /**
     * Represents the grain resource.
     */
    GRAIN("grain"),

    /**
     * Represents the wool resource.
     */
    WOOL("wool");

    /**
     * The label associated with the resource type.
     */
    public final String label;

    /**
     * Constructs a new ResourceType with the given label.
     *
     * @param label the label associated with the resource type
     */
    private ResourceType(String label) {
        this.label = label;
    }

    /**
     * Returns the ResourceType corresponding to the given index in the values array.
     *
     * @param index the index of the desired ResourceType
     * @return the ResourceType corresponding to the given index
     */
    public static ResourceType getByIndex(int index) {
        return ResourceType.values()[index];
    }
}
