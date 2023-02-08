package com.example.thesettlers;
import com.example.thesettlers.enums.TerrainType;

public class Tile {
    private TerrainType tileType;
    private int value;
    private boolean robber;

    public Tile(TerrainType type, int value){
        tileType = type;
        this.value = value;
        robber = false;
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