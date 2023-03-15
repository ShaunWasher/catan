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
    private Game game;
    private String[] playerColours;

    public Road(double x, double y, int version, Game game){
        this.game = game;
        settlementA = null;
        settlementB = null;
        owner = null;
        playerColours = new String[]{"red", "blue", "gold", "white"};
        rectangle = new Rectangle(x,y,35,35);
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
        rectangle.setOnMouseClicked(e -> {
            if(game.buyRoad(this)){
                rectangle.setX(x - 13);
                rectangle.setY(y - 13);
                rectangle.setHeight(61);
                rectangle.setWidth(61);
                rectangle.setFill(new ImagePattern(getImage(version)));
            }
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
            this.image = new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"road1.png").toExternalForm());
        } else if (version == 2) {
            this.image = new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"road2.png").toExternalForm());
        } else if (version == 3) {
            this.image = new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"road3.png").toExternalForm());
        } else {
        }
        return this.image;
    }

}
