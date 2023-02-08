package com.example.thesettlers;
import com.example.thesettlers.enums.TerrainType;

public class Tile {
    private TerrainType tileType;
    private int value;
    private boolean robber;
    private Hexagon hexagon;


    public Tile(double x, double y, TerrainType tileType, int value){
        this.tileType = tileType;
        this.value = value;
        robber = false;
        hexagon = new Hexagon(x,y,tileType);
    }

    public Hexagon getHexagon() {
        return hexagon;
    }

    public Hexagon createHexagon(int x, int y){
        hexagon = new Hexagon(x,y,tileType);
        return hexagon;
    }

    public void setRobber(boolean robber) {
        this.robber = robber;
    }

    public boolean getRobber() {
        return robber;
    }

    public int getValue() {
        return value;
    }

    public TerrainType getTileType() {
        return tileType;
    }
}