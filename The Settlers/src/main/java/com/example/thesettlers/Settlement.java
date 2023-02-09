package com.example.thesettlers;

public class Settlement {
    private Road[] roads;
    private Tile[] tiles;
    private Player owner;
    boolean isCity;
    public Settlement(Road[] roads, Tile[] tiles){
        this.roads = roads;
        this.tiles = tiles;
        owner = null;
        isCity = false;
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

    public Road[] getRoads() {
        return roads;
    }

    public Tile[] getTiles() {
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
