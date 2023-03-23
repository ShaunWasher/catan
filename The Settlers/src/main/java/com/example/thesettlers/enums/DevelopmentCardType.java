package com.example.thesettlers.enums;
public enum DevelopmentCardType {
    KNIGHT("knight"),
    VP("vp"),
    ROADBUILDING("roadbuilding"),
    YEAROFPLENTY("yearofplenty"),
    MONOPOLY("monopoly");

    public final String label;

    private DevelopmentCardType(String label) {
        this.label = label;
    }

}
