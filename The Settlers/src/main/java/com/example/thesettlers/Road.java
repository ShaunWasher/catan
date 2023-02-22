package com.example.thesettlers;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Road {
    private Player owner;
    private Settlement settlementA;
    private Settlement settlementB;
    private Rectangle rectangle;
    private Image image;

    public Road(double x, double y, int version){
        settlementA = null;
        settlementB = null;
        owner = null;
        rectangle = new Rectangle(x,y,35,35);
        rectangle.setFill(new ImagePattern(getImage(version)));
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
        rectangle.setOnMouseClicked(e -> {
            rectangle.setX(x - 13);
            rectangle.setY(y - 13);
            rectangle.setHeight(61);
            rectangle.setWidth(61);
            rectangle.setFill(new ImagePattern(getImage(version)));
        });
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

    public void addSettlements(Settlement settlementA, Settlement settlementB) {
        this.settlementA = settlementA;
        this.settlementB = settlementB;
    }
    public Rectangle getIcon() {
        return rectangle;
    }
    public Image getImage(int version) {
        if (version == 1) {
            this.image = new Image(this.getClass().getResource("redroad1.png").toExternalForm());
        } else if (version == 2) {
            this.image = new Image(this.getClass().getResource("redroad2.png").toExternalForm());
        } else if (version == 3) {
            this.image = new Image(this.getClass().getResource("redroad3.png").toExternalForm());
        } else {
        }
        return this.image;
    }

}
