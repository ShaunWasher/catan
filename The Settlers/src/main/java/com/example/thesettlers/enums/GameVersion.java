package com.example.thesettlers.enums;

/**
 * The GameVersion enumeration represents the two different
 * types of game version in the game The Settlers.
 */
public enum GameVersion {
    /**
     * VP represents a game where the first player to reach 10 victory
     * points wins
     */
    VP,

    /**
     * TIMED represents a game that lasts a specific amount of time, after
     * which the player with the highest number of victory points wins
     */
    TIMED
}
