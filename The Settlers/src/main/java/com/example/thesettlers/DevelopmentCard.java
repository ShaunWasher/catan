/**
 * The DevelopmentCard class represents a development card in the game The Settlers.
 */

package com.example.thesettlers;

import com.example.thesettlers.enums.DevelopmentCardType;

public class DevelopmentCard {

    /**
     * The type of the development card.
     */
    private DevelopmentCardType cardType;

    /**
     * Constructs a new DevelopmentCard object with the specified card type.
     *
     * @param type the type of the development card
     */
    public DevelopmentCard(DevelopmentCardType type){
        cardType = type;
    }

    /**
     * Returns the type of the development card.
     *
     * @return the type of the development card
     */
    public DevelopmentCardType getCardType() {
        return cardType;
    }
}