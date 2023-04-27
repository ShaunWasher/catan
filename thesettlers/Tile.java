package com.example.thesettlers;

import com.example.thesettlers.enums.TerrainType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.Objects;

import static java.lang.Math.sqrt;

public class Tile extends Polygon {
    private Image image;
    private final static double r = 70; // the inner radius from hexagon center to outer corner
    private final static double n = sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private TerrainType tileType;// Represents the types of terrains in the gameboard.
    private int value;;//The value associated with the tile
    private boolean robber;
    private ValueLabel valueLabel;
    public Tile(double x, double y, String type, int value) {
        this.value = value;
        robber = false;
        if (Objects.equals(type, "HILLS")) {//if-else statement to compare the value of variable "type against different strings(Mountain, Forest,...)
            tileType = TerrainType.HILLS;// Check if "type" is equal to "HILLS", if yes then the value of "tileType" is set to a value called "TerrainType.Hills".
        } else if (Objects.equals(type, "FOREST")) {// Check if "type" is equal to "FOREST", if yes then the value of "tileType" is set to a value called "TerrainType.FOREST".
            tileType = TerrainType.FOREST;
        }  else if (Objects.equals(type, "MOUNTAINS")) {// Check if "type" is equal to "MOUNTAIN", if yes then the value of "tileType" is set to a value called "TerrainType.MOUNTAIN". {
            tileType = TerrainType.MOUNTAINS;
        } else if (Objects.equals(type, "FIELDS")) {// Check if "type" is equal to "FIELDS", if yes then the value of "tileType" is set to a value called "TerrainType.FIELDS".
            tileType = TerrainType.FIELDS;
        } else if (Objects.equals(type, "PASTURE")) {// Check if "type" is equal to "PASTURE", if yes then the value of "tileType" is set to a value called "TerrainType.PASTURE".
            tileType = TerrainType.PASTURE;
        } else if (Objects.equals(type, "DESERT")) {// Check if "type" is equal to "DESERT", if yes then the value of "tileType" is set to a value called "TerrainType.DESERT".
            tileType = TerrainType.DESERT;
        } else { }// If none of the conditions are met, "tileType" remains unchanged.
        getPoints().addAll(
                x, y,
                x, y + r,
                x + n, y + r * 1.5,
                x + (2*n), y + r,
                x + (2*n), y,
                x + n, y - r * 0.5
        );

        setFill(new ImagePattern(getImage(tileType)));
        setStrokeWidth(6);
        setStroke(Color.web("f9c872"));
        setOnMouseClicked(e -> System.out.println("Clicked: " + this));
        valueLabel = new ValueLabel(x, y, value);
    }

    public Circle getValueLabel(){
        return valueLabel.getCircle();
    }

    public void setRobber(boolean robber) {
        this.robber = robber;
    }//Sets the robber.

    public boolean getRobber() {
        return robber;
    }// Getter method that gets the robber and returns it.

    public int getValue() {
        return value;
    }// Getter method that gets the value and returns it.

    public TerrainType getTileType() {
        return tileType;
    }//Getter method that gets the Tile Type and returns it

    public Image getImage(TerrainType type) {
        if (Objects.equals(type, TerrainType.HILLS)) {
            this.image = new Image(this.getClass().getResource("hills.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.FOREST)) {
            this.image = new Image(this.getClass().getResource("forest.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.MOUNTAINS)) {
            this.image = new Image(this.getClass().getResource("mountains.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.FIELDS)) {
            this.image = new Image(this.getClass().getResource("fields.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.PASTURE)) {
            this.image = new Image(this.getClass().getResource("pasture.png").toExternalForm());
        } else if (Objects.equals(type, TerrainType.DESERT)) {
            this.image = new Image(this.getClass().getResource("desert.png").toExternalForm());
        } else {
            this.image = new Image(this.getClass().getResource("image.png").toExternalForm());
        }
        return this.image;
    }
}