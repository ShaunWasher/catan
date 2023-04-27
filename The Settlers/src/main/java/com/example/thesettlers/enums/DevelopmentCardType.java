package com.example.thesettlers.enums;


/**
 * The DevelopmentCardType enumeration represents the different types of development cards
 * in the game of The Settlers. Each value corresponds to a specific type of development card.
 */
public enum DevelopmentCardType {
    /**
     * KNIGHT represents a knight card, which allows a player to move the robber
     * and potentially steal resources from other players.
     */
    KNIGHT("knight"),

    /**
     * VP represents a victory point card, which provides the player with an additional
     * victory point towards winning the game.
     */
    VP("vp"),

    /**
     * ROADBUILDING represents a road building card, which allows the player to
     * build two roads for free.
     */
    ROADBUILDING("roadbuilding"),

    /**
     * YEAROFPLENTY represents a year of plenty card, which allows the player to
     * obtain any two resources from the bank.
     */
    YEAROFPLENTY("yearofplenty"),

    /**
     * MONOPOLY represents a monopoly card, which allows the player to take all of
     * one type of resource from all other players.
     */
    MONOPOLY("monopoly");

    /**
     * The label of the development card type.
     */
    public final String label;

    /**
     * Constructs a DevelopmentCardType instance with the specified label.
     *
     * @param label The label of the development card type.
     */
    private DevelopmentCardType(String label) {
        this.label = label;
    }

}
