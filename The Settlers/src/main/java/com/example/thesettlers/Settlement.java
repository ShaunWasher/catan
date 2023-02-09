package com.example.thesettlers;

import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Settlement {
    private ArrayList<Road> roads;
    private ArrayList<Tile> tiles;
    private Player owner;
    boolean isCity;
    Icon icon;
    public Settlement(double x, double y){
        this.roads = new ArrayList<>();
        this.tiles = new ArrayList<>();
        owner = null;
        isCity = false;
        icon = new Icon(x, y, owner);
    }
    public Rectangle getIcon() {
        return icon.getRectangle();
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
}
