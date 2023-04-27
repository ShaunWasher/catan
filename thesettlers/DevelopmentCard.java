package com.example.thesettlers;
//This class represents the cards in the game.
import com.example.thesettlers.enums.DevelopmentCardType;

public class DevelopmentCard {
    DevelopmentCardType cardType;
    public DevelopmentCard(DevelopmentCardType type){
        cardType = type;// Each card has a type stored in the variable "cardType"
    }

    public DevelopmentCardType getCardType() {//The type of the card can be obtained using "getCardType".
        return cardType;//Returns the type of the card.
    }
}
