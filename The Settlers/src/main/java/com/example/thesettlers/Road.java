package com.example.thesettlers;

import javafx.scene.shape.Rectangle;

public class Road {
    private Player owner;
    private Settlement settlementA;
    private Settlement settlementB;
    RoadIcon icon;

    public Road(double x, double y, int version){
        settlementA = null;
        settlementB = null;
        owner = null;
        icon = new RoadIcon(x, y, version);
    }

    public Rectangle getIcon() {
        return icon.getRectangle();
    }
    public Player getOwner() {
        return owner;
    }

    public Settlement getSettlementA() {
        return settlementA;
    }

    public Settlement getSettlementB() {
        return settlementB;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Settlement getNextSettlement(Settlement startingSettlement){
        if(startingSettlement == settlementA){
            return settlementB;
        }
        else if(startingSettlement == settlementB){
            return settlementA;
        }
        return null;
    }
}
