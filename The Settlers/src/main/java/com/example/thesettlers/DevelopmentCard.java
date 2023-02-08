package com.example.thesettlers;

public class DevelopmentCard {
    DevelopmentCardType cardType;
    public DevelopmentCard(DevelopmentCardType type){
        cardType = type;
    }

    public DevelopmentCardType getCardType() {
        return cardType;
    }
}
