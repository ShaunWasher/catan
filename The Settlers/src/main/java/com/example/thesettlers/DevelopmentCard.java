package com.example.thesettlers;
import com.example.thesettlers.enums.DevelopmentCardType;
public class DevelopmentCard {
    DevelopmentCardType cardType;
    public DevelopmentCard(DevelopmentCardType type){
        cardType = type;
    }

    public DevelopmentCardType getCardType() {
        return cardType;
    }
}
