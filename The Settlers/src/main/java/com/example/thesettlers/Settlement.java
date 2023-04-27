package com.example.thesettlers;

import com.example.thesettlers.enums.PortType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Settlement {
    private ArrayList<Road> roads;
    private ArrayList<Tile> tiles;
    private Player owner;
    boolean isCity;
    public Rectangle rectangle;
    private Game game;
    private PortType port;

    public Settlement(double x, double y, Game game){
        this.game = game;
        this.roads = new ArrayList<>();
        this.tiles = new ArrayList<>();
        String[] playerColours =  {"red","blue","gold","white"};
        owner = null;
        isCity = false;
        rectangle = new Rectangle(x,y,35,35);
        rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource("placementcircle.png").toExternalForm())));
        rectangle.setOnMouseClicked(e -> {
            System.out.println(port);
            if(owner == null) {
                if (game.buySettlement(this)) {
                    rectangle.setX(x - 5);
                    rectangle.setY(y - 5);
                    rectangle.setHeight(45);
                    rectangle.setWidth(45);
                    rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"settlement.png").toExternalForm())));
                    game.getGameBoard().getSettlementPermPane().getChildren().add(rectangle);
                    for(Settlement settlement:game.getGameBoard().getSettlementList()){
                        for (Road road : settlement.getRoads()) {
                            if (road.getNextSettlement(settlement).getOwner() != null) {
                                settlement.getIcon().setVisible(false);
                                break;
                            } else {
                                settlement.getIcon().setVisible(true);
                            }
                        }
                    }
                }
            } else {
                if(game.upgradeToCity(this)){
                    rectangle.setFill(new ImagePattern(new Image(this.getClass().getResource(playerColours[game.getCurrentPlayer().getPlayerID()-1]+"city.png").toExternalForm())));
                }
            }
        });
    }

    //check if there is a road connection to the player
    public boolean checkRoadConnection(Player player){
        for(Road road: roads){
            if(road.getOwner() == player){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void makeCity() {
        isCity = true;
    }

    public boolean isCity() {
        return isCity;
    }

    public void addRoad(Road road){
        roads.add(road);
    }
    public void addTile(Tile tile){
        tiles.add(tile);
    }
    public Rectangle getIcon() {
        return rectangle;
    }

    public void setPort(PortType port) {
        this.port = port;
    }

    public PortType getPort() {
        return port;
    }
}
