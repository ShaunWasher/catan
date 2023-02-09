package com.example.thesettlers;
import com.example.thesettlers.enums.TerrainType;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Tile {
    private TerrainType tileType;
    private int value;
    private boolean robber;
    private Hexagon hexagon;
    private ValueLabel valueLabel;
    public Tile(double x, double y, String type, int value) {
        this.value = value;
        robber = false;
        if (Objects.equals(type, "HILLS")) {
            tileType = TerrainType.HILLS;
        } else if (Objects.equals(type, "FOREST")) {
            tileType = TerrainType.FOREST;
        } else if (Objects.equals(type, "MOUNTAINS")) {
            tileType = TerrainType.MOUNTAINS;
        } else if (Objects.equals(type, "FIELDS")) {
            tileType = TerrainType.FIELDS;
        } else if (Objects.equals(type, "PASTURE")) {
            tileType = TerrainType.PASTURE;
        } else if (Objects.equals(type, "DESERT")) {
            tileType = TerrainType.DESERT;
        } else { }
        hexagon = new Hexagon(x,y,tileType);
        valueLabel = new ValueLabel(x, y, value);
    }

    public Hexagon getHexagon() {
        return hexagon;
    }
    public Circle getValueLabel(){
        return valueLabel.getCircle();
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